package com.yoursway.sadr.python_v2.constructs;

import static com.yoursway.sadr.python_v2.constructs.Effects.NONE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGoal;

public abstract class PythonConstructImpl<N extends ASTNode> implements PythonConstruct {
    
    protected final N node;
    private final Scope parentScope;
    private List<PythonConstruct> preChildren = Collections.EMPTY_LIST;
    private List<PythonConstruct> postChildren;
    private PythonConstructImpl<ASTNode> sintacticallyPreviousConstruct;
    
    public PythonConstructImpl(Scope sc, N node) {
        this.parentScope = sc;
        this.node = node;
        wrapEnclosedChildren();
        if (postChildren == null) {
            throw new IllegalStateException();
        }
    }
    
    protected void setSintacticallyPreviousConstruct(PythonConstruct construct) {
        sintacticallyPreviousConstruct = (PythonConstructImpl<ASTNode>) construct;
    }
    
    public PythonConstruct getSyntacticallyPreviousConstruct() {
        return sintacticallyPreviousConstruct;
    }
    
    protected void wrapEnclosedChildren() {
        //TODO pre- & post- children.
        setPostChildren(PythonConstructFactory.wrap(this.node.getChilds(), innerScope()));
    }
    
    public PythonConstruct staticallyEnclosingConstruct() {
        throw new UnsupportedOperationException();
    }
    
    public N node() {
        return node;
    }
    
    public PythonConstruct subconstructFor(ASTNode node) {
        PythonConstruct result = innerSubsonstructFor(node);
        if (result == null)
            throw new IllegalArgumentException("Subconstruct not found for node "
                    + node.getClass().getSimpleName() + " in " + this);
        return result;
    }
    
    private PythonConstruct innerSubsonstructFor(ASTNode node) {
        if (isNode(node))
            return this;
        // TODO check offset here
        for (PythonConstruct c : postChildren) {
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
    //        List<PythonConstruct> constructs = filter(childConstructs(), NOT_METHOD);
    //        return continuation.process(
    //                new ControlFlowGraph<PythonConstruct, Scope, PythonDynamicContext, ASTNode>(
    //                        constructs), requestor);
    //    }
    
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
        return (node == other.node);
    }
    
    public Scope parentScope() {
        return parentScope;
    }
    
    public Scope innerScope() {
        return parentScope;
    }
    
    public List<PythonConstruct> getPostChildren() {
        return postChildren;
    }
    
    protected void setPostChildren(List<PythonConstruct> constructs) {
        if (constructs == null) {
            throw new IllegalArgumentException("setPostChildren(null) called!");
        }
        postChildren = constructs;
    }
    
    protected void setPreChildren(List<PythonConstruct> constructs) {
        if (constructs == null) {
            throw new IllegalArgumentException("setPreChildren(null) called!");
        }
        preChildren = constructs;
    }
    
    @Override
    public String toString() {
        return node.toString();
    }
    
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        throw new UnsupportedOperationException("Evaluate is not enabled for node class "
                + this.node.getClass().getSimpleName());
    }
    
    public Frog toFrog() {
        throw new UnsupportedOperationException("All frogs gone to heaven, except "
                + this.getClass().getSimpleName() + " of " + this.node.getClass().getSimpleName());
    }
    
    public Effects getEffects() {
        return NONE;
    }
    
    public List<PythonConstruct> getPreChildren() {
        return preChildren;
    }
    
    protected void setupPrevConstructRelation(PythonConstruct prev) {
        for (PythonConstruct child : getPreChildren()) {
            PythonConstructImpl<ASTNode> pyChild = (PythonConstructImpl<ASTNode>) child;
            pyChild.setupPrevConstructRelation(prev);
        }
        setSintacticallyPreviousConstruct(prev);
        for (PythonConstruct child : getPostChildren()) {
            PythonConstructImpl<ASTNode> pyChild = (PythonConstructImpl<ASTNode>) child;
            pyChild.setupPrevConstructRelation(this);
        }
    }
}
