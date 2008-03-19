package com.yoursway.sadr.python.core.runtime.contributions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.ArrayListHashMultiMap;
import com.yoursway.sadr.python.core.runtime.PythonRuntimeModel;
import com.yoursway.sadr.python.core.typeinferencing.constructs.CallC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexRequest;
import com.yoursway.sadr.python.core.typeinferencing.goals.AssignmentInfo;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.python.core.typeinferencing.services.AssignmentsRequestor;
import com.yoursway.sadr.python.core.typeinferencing.services.CallsRequestor;
import com.yoursway.sadr.python.core.typeinferencing.services.OuteriorNodeLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.SearchService;

public class FileContributionsManager implements OuteriorNodeLookup, SearchService {
    
    private final Map<ISourceModule, DtlFile> files = new HashMap<ISourceModule, DtlFile>();
    
    private final PythonRuntimeModel model;
    
    public FileContributionsManager(PythonRuntimeModel model) {
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
    
    public void addToIndex(PythonConstruct root, ContinuationRequestor requestor,
            SimpleContinuation continuation) {
        FileScope file = root.staticContext().nearestScope().fileScope();
        lookup(file).addToIndex(root, requestor, continuation);
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
        
        public PythonRuntimeModel model() {
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
        
        public void addToIndex(PythonConstruct root, ContinuationRequestor requestor,
                SimpleContinuation continuation) {
            index.add(root, requestor, continuation);
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
        
        private final AbstractMultiMap<String, CallC> procedureCalls = new ArrayListHashMultiMap<String, CallC>();
        
        private final AbstractMultiMap<String, CallC> methodsCalls = new ArrayListHashMultiMap<String, CallC>();
        
        private final AbstractMultiMap<String, AssignmentInfo> assignments = new ArrayListHashMultiMap<String, AssignmentInfo>();
        
        private final FileScope scope;
        
        public Index(FileScope scope) {
            this.scope = scope;
        }
        
        public void add(PythonConstruct root, ContinuationRequestor requestor, SimpleContinuation continuation) {
            IndexRequest request = new IndexRequest(methodsCalls, procedureCalls, assignments);
            root.staticContext().propagationTracker().traverseStatically(root, request, requestor,
                    continuation);
        }
        
        public void findAssignments(String name, AssignmentsRequestor requestor) {
            for (AssignmentInfo info : assignments.get(name.toLowerCase()))
                requestor.assignment(info, scope);
        }
        
        public void findMethodCalls(String name, CallsRequestor requestor) {
            for (CallC call : methodsCalls.get(name.toLowerCase()))
                requestor.call(call);
        }
        
        public void findProcedureCalls(String name, CallsRequestor requestor) {
            for (CallC call : procedureCalls.get(name.toLowerCase()))
                requestor.call(call);
        }
    }
    
    // this method was left in memorial purposes
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
