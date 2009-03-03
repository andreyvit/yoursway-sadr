package com.yoursway.sadr.python_v2.constructs;

import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.ContextSensitiveGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public abstract class PythonConstructImpl<N extends ASTNode> implements PythonConstruct {
    
    protected final N node;
    private final Scope parentScope;
    private List<PythonConstruct> preChildren = Collections.emptyList();
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
    
    protected void setSyntacticallyPreviousConstruct(PythonConstruct construct) {
        sintacticallyPreviousConstruct = (PythonConstructImpl<ASTNode>) construct;
    }
    
    public PythonConstruct getSyntacticallyPreviousConstruct() {
        return sintacticallyPreviousConstruct;
    }
    
    /**
     * NB: always called from constructor
     */
    protected void wrapEnclosedChildren() {
        //TODO pre- & post- children.
        setPostChildren(PythonConstructFactory.wrap(this.node.getChilds(), scope()));
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
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((node == null) ? 0 : node.hashCode());
        //FIXME: Add root element comparison, to check nodes belong to the same tree!
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
        //FIXME: Add root element comparison, to check nodes belong to the same tree!
        return (node == other.node);
    }
    
    public Scope parentScope() {
        return parentScope;
    }
    
    public Scope scope() {
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
    
    public ContextSensitiveGoal execute(Krocodile context) {
        throw new UnsupportedOperationException("Evaluate is not enabled for node class "
                + this.node.getClass().getSimpleName());
    }
    
    public PythonValueSet evaluate(Krocodile context) {
        return execute(context).evaluate();
    }
    
    public List<PythonConstruct> getPreChildren() {
        return preChildren;
    }
    
    protected void setupPrevConstructRelation(PythonConstruct prev) {
        for (PythonConstruct child : getPreChildren()) {
            PythonConstructImpl<ASTNode> pyChild = (PythonConstructImpl<ASTNode>) child;
            pyChild.setupPrevConstructRelation(prev);
        }
        setSyntacticallyPreviousConstruct(prev);
        for (PythonConstruct child : getPostChildren()) {
            PythonConstructImpl<ASTNode> pyChild = (PythonConstructImpl<ASTNode>) child;
            pyChild.setupPrevConstructRelation(this);
        }
    }
    
    /**
     * 
     * @param prev
     *            previous construct
     * @param constructs
     *            constructs to be sequentially arranged with <code>prev</code>
     *            as the most previous construct.
     * @return last construct from <code>constructs</code>.
     */
    protected PythonConstruct setPrevConstructRelation(PythonConstruct prev, List<PythonConstruct> constructs) {
        for (PythonConstruct c : constructs) {
            PythonConstructImpl<ASTNode> construct = (PythonConstructImpl<ASTNode>) c;
            construct.setSyntacticallyPreviousConstruct(prev);
            prev = c;
        }
        return prev;
    }
}
