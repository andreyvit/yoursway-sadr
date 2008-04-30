package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.google.common.base.Predicate;
import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public abstract class PythonConstructImpl<N extends ASTNode> implements PythonConstruct {
    
    protected final N node;
    private final Scope parentScope;
    private final List<PythonConstruct> enclosedConstructs;
    
    public PythonConstructImpl(Scope sc, N node) {
        this.parentScope = sc;
        this.node = node;
        
        List<ASTNode> children = this.node.getChilds();
        enclosedConstructs = new ArrayList<PythonConstruct>(children.size());
        for (ASTNode child : children) {
            enclosedConstructs.add(wrap(child));
        }
    }
    
    public PythonConstruct staticallyEnclosingConstruct() {
        throw new UnsupportedOperationException();
    }
    
    public N node() {
        return node;
    }
    
    protected PythonConstruct wrap(ASTNode node) {
        return PythonConstructFactory.wrapConstruct(node, parentScope);
    }
    
    public PythonConstruct subconstructFor(ASTNode node) {
        PythonConstruct result = innerSubsonstructFor(node);
        if (result == null)
            throw new IllegalArgumentException("Subconstruct not found for node "
                    + node.getClass().getSimpleName() + " in " + this);
        return result;
    }
    
    PythonConstruct innerSubsonstructFor(ASTNode node) {
        if (isNode(node))
            return this;
        // TODO check offset here
        for (PythonConstruct c : enclosedConstructs) {
            PythonConstruct sc = ((PythonConstructImpl<?>) c).innerSubsonstructFor(node);
            if (sc != null)
                return sc;
        }
        return null;
    }
    
    protected boolean isNode(ASTNode node) {
        return node == this.node;
    }
    
    //    public ContinuationRequestorCalledToken calculateEffectiveControlFlowGraph(
    //            ContinuationScheduler requestor,
    //            ControlFlowGraphRequestor<PythonConstruct, Scope, PythonDynamicContext, ASTNode> continuation) {
    //        List<PythonConstruct> constructs = filter(enclosedConstructs(), NOT_METHOD);
    //        return continuation.process(
    //                new ControlFlowGraph<PythonConstruct, Scope, PythonDynamicContext, ASTNode>(
    //                        constructs), requestor);
    //    }
    
    @SuppressWarnings("unchecked")
    protected List<ASTNode> enclosedNodes() {
        return node.getChilds();
    }
    
    protected static final Predicate<PythonConstruct> NOT_METHOD = new Predicate<PythonConstruct>() {
        
        public boolean apply(PythonConstruct t) {
            return !(t instanceof MethodDeclarationC);
        }
        
    };
    
    public Collection<MumblaWumblaThreesome> mumblaWumbla() {
        return null;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((node == null) ? 0 : node.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PythonConstructImpl other = (PythonConstructImpl) obj;
        if (node == null) {
            if (other.node != null)
                return false;
        } else if (!node.equals(other.node))
            return false;
        return true;
    }
    
    public Scope parentScope() {
        return parentScope;
    }
    
}
