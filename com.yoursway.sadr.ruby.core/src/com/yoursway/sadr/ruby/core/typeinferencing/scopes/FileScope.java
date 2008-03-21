package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.ArrayListHashMultiMap;
import com.yoursway.sadr.ruby.core.ast.visitor.RubyAstTraverser;
import com.yoursway.sadr.ruby.core.ast.visitor.RubyAstVisitor;
import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.runtime.contributions.NodeBoundItem;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.services.NodeLookup;

public class FileScope extends LocalScope implements NodeLookup {
    
    private final ISourceModule file;
    
    private final Map<ASTNode, ASTNode> parents = new HashMap<ASTNode, ASTNode>();
    
    private final AbstractMultiMap<ASTNode, ModuleDeclaration> extentions = new ArrayListHashMultiMap<ASTNode, ModuleDeclaration>();
    
    public FileScope(RootScope parent, ISourceModule file, ModuleDeclaration node) {
        super(parent, node);
        this.file = file;
    }
    
    /**
     * There are might be several <code>ModuleDeclaration</code>s per file
     * (one root plus one per each eval).
     * 
     * @param fakeParent
     */
    public void calculateParents(ModuleDeclaration node, ASTNode fakeParent) {
        new RubyAstTraverser().traverse(node, new ParentRecordingVisitor());
        if (fakeParent != null) {
            parents.put(node, fakeParent);
            extentions.put(fakeParent, node);
        }
    }
    
    class ParentRecordingVisitor extends RubyAstVisitor<ASTNode> {
        
        public ParentRecordingVisitor() {
            super(null);
        }
        
        public ParentRecordingVisitor(RubyAstVisitor<?> parentVisitor) {
            super(parentVisitor);
        }
        
        @Override
        protected RubyAstVisitor<?> enterNode(ASTNode childNode) {
            parents.put(childNode, node());
            return new ParentRecordingVisitor(this);
        }
        
    }
    
    @Override
    public ModuleDeclaration node() {
        return (ModuleDeclaration) super.node();
    }
    
    @Override
    public NodeLookup nodeLookup() {
        return this;
    }
    
    public ISourceModule file() {
        return file;
    }
    
    @Override
    protected String leafToString() {
        return file.getElementName();
    }
    
    public NodeBoundItem lookup(ASTNode node) {
        return ((RootScope) parent).outeriorNodeLookup().lookup(file, node);
    }
    
    @Override
    public RubyVariable findOwnVariable(String name) {
        return null; // none for now
    }
    
    public ValueInfo selfType() {
        return null;
    }
    
    public ASTNode parentOf(ASTNode node) {
        return parents.get(node);
    }
    
    @Override
    public Collection<ModuleDeclaration> extentionsOf(ASTNode node) {
        Collection<ModuleDeclaration> result = extentions.get(node);
        if (result == null)
            return Collections.emptyList();
        else
            return result;
    }
    
    @Override
    public FileScope fileScope() {
        return this;
    }
    
    @Override
    public RubyClass currentClass() {
        return null;
    }
    
}
