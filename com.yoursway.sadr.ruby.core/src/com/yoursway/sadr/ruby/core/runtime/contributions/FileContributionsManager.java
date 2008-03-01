package com.yoursway.sadr.ruby.core.runtime.contributions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.ArrayListHashMultiMap;
import com.yoursway.sadr.ruby.core.ast.visitor.RubyAstTraverser;
import com.yoursway.sadr.ruby.core.ast.visitor.RubyAstVisitor;
import com.yoursway.sadr.ruby.core.runtime.RubyRuntimeModel;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.AssignmentInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.BasicAssignmentVisitor;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.MethodScope;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.ProcedureScope;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.ruby.core.typeinferencing.services.AssignmentsRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.services.CallsRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.services.OuteriorNodeLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.SearchService;

public class FileContributionsManager implements OuteriorNodeLookup, SearchService {
    
    private final Map<ISourceModule, DtlFile> files = new HashMap<ISourceModule, DtlFile>();
    
    private final RubyRuntimeModel model;
    
    public FileContributionsManager(RubyRuntimeModel model) {
        this.model = model;
    }
    
    public Context createContext(FileScope fileScope) {
        return new ContextImpl(lookup(fileScope));
    }
    
    public NodeBoundItem lookup(ISourceModule file, ASTNode node) {
        DtlFile result = files.get(file);
        if (result == null)
            return null;
        return result.lookup(node);
    }
    
    private DtlFile lookup(FileScope fileScope) {
        ISourceModule file = fileScope.file();
        DtlFile result = files.get(file);
        if (result == null) {
            result = new DtlFile(file, fileScope);
            files.put(file, result);
        }
        return result;
    }
    
    public void addToIndex(FileScope file, ModuleDeclaration node) {
        lookup(file).addToIndex(node);
    }
    
    class ContextImpl implements Context {
        
        private final DtlFile file;
        
        public ContextImpl(DtlFile file) {
            this.file = file;
        }
        
        public void add(ContributableItem item) {
            file.add(item);
        }
        
        public ISourceModule file() {
            return file.file();
        }
        
        public RubyRuntimeModel model() {
            return model;
        }
        
        public FileScope fileScope() {
            return file.scope();
        }
        
    }
    
    class DtlFile {
        
        private final Collection<ContributableItem> items = new ArrayList<ContributableItem>();
        
        private final Map<ASTNode, NodeBoundItem> nodesToItems = new HashMap<ASTNode, NodeBoundItem>();
        
        private final ISourceModule file;
        
        private final FileScope scope;
        
        private final Index index;
        
        public DtlFile(ISourceModule file, FileScope scope) {
            this.file = file;
            this.scope = scope;
            index = new Index(scope);
        }
        
        public void addToIndex(ModuleDeclaration node) {
            index.add(node);
        }
        
        public Index index() {
            return index;
        }
        
        public ISourceModule file() {
            return file;
        }
        
        public FileScope scope() {
            return scope;
        }
        
        public void add(ContributableItem item) {
            items.add(item);
            if (item instanceof NodeBoundItem)
                bind((NodeBoundItem) item);
        }
        
        private void bind(NodeBoundItem item) {
            nodesToItems.put(item.node(), item);
        }
        
        public NodeBoundItem lookup(ASTNode node) {
            return nodesToItems.get(node);
        }
        
    }
    
    static class Index {
        
        private final AbstractMultiMap<String, CallExpression> procedureCalls = new ArrayListHashMultiMap<String, CallExpression>();
        
        private final AbstractMultiMap<String, CallExpression> methodsCalls = new ArrayListHashMultiMap<String, CallExpression>();
        
        private final AbstractMultiMap<String, AssignmentInfo> assignments = new ArrayListHashMultiMap<String, AssignmentInfo>();
        
        private final FileScope scope;
        
        public Index(FileScope scope) {
            this.scope = scope;
        }
        
        public void add(ModuleDeclaration module) {
            RubyAstTraverser traverser = new RubyAstTraverser();
            traverser.traverse(module, new CallsIndexer(procedureCalls, methodsCalls));
            traverser.traverse(module, new AssignmentsIndexer(assignments, scope));
        }
        
        public void findAssignments(String name, AssignmentsRequestor requestor) {
            for (AssignmentInfo info : assignments.get(name.toLowerCase()))
                requestor.assignment(info, scope);
        }
        
        public void findMethodCalls(String name, CallsRequestor requestor) {
            for (CallExpression call : methodsCalls.get(name.toLowerCase()))
                requestor.call(call, scope);
        }
        
        public void findProcedureCalls(String name, CallsRequestor requestor) {
            for (CallExpression call : procedureCalls.get(name.toLowerCase()))
                requestor.call(call, scope);
        }
    }
    
    static class CallsIndexer extends RubyAstVisitor<ASTNode> {
        
        private final AbstractMultiMap<String, CallExpression> procedureCalls;
        private final AbstractMultiMap<String, CallExpression> methodsCalls;
        
        public CallsIndexer(AbstractMultiMap<String, CallExpression> procedureCalls,
                AbstractMultiMap<String, CallExpression> methodsCalls) {
            super(null);
            this.procedureCalls = procedureCalls;
            this.methodsCalls = methodsCalls;
        }
        
        @Override
        protected RubyAstVisitor<?> enterCallExpression(CallExpression node) {
            String name = node.getName().toLowerCase();
            if (node.getReceiver() == null)
                procedureCalls.put(name, node);
            else
                methodsCalls.put(name, node);
            return this;
        }
        
    }
    
    static class AssignmentsIndexer extends BasicAssignmentVisitor {
        
        private final AbstractMultiMap<String, AssignmentInfo> assignments;
        private final FileScope fileScope;
        
        public AssignmentsIndexer(AbstractMultiMap<String, AssignmentInfo> assignments, FileScope fileScope) {
            super(null);
            this.assignments = assignments;
            this.fileScope = fileScope;
            this.scope = fileScope;
        }
        
        public AssignmentsIndexer(AssignmentsIndexer parentVisitor, Scope newScope) {
            super(parentVisitor);
            this.scope = newScope;
            this.assignments = parentVisitor.assignments;
            this.fileScope = parentVisitor.fileScope;
        }
        
        @Override
        protected void matched(ASTNode terminal, AssignmentInfo info) {
            if (terminal instanceof SimpleReference) {
                SimpleReference symbol = (SimpleReference) terminal;
                String name = symbol.getName().toLowerCase();
                assignments.put(name, info);
            }
        }
        
        @Override
        protected RubyAstVisitor<?> enterMethodDeclaration(MethodDeclaration node) {
            // FIXME FOR scopes are not handled in the indexer
            Scope newScope = RubyUtils.restoreScope(fileScope, node);
            if (!(newScope instanceof MethodScope || newScope instanceof ProcedureScope))
                throw new AssertionError("Incorrect scope restored in indexer's enterProcedureDecl");
            return new AssignmentsIndexer(this, newScope);
        }
        
        @Override
        protected boolean matches(ASTNode terminal) {
            return true;
        }
        
    }
    
    public FileScope[] searchForEverything() {
        List<FileScope> result = new ArrayList<FileScope>();
        for (DtlFile file : files.values())
            result.add(file.scope());
        return result.toArray(new FileScope[result.size()]);
    }
    
    public void findAssignments(String name, AssignmentsRequestor requestor) {
        for (DtlFile file : files.values())
            file.index().findAssignments(name, requestor);
    }
    
    public void findMethodCalls(String name, CallsRequestor requestor) {
        for (DtlFile file : files.values())
            file.index().findMethodCalls(name, requestor);
    }
    
    public void findProcedureCalls(String name, CallsRequestor requestor) {
        for (DtlFile file : files.values())
            file.index().findProcedureCalls(name, requestor);
    }
    
}
