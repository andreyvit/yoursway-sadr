package com.yoursway.sadr.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;


public abstract class AbstractConstruct extends AbstractSubject implements Construct {
    
    private final Scope scope;
    private final ASTNode node;
    
    public AbstractConstruct(Scope scope, ASTNode node) {
        this.scope = scope;
        this.node = node;
    }
    
    public Scope scope() {
        return scope;
    }
    
    public ASTNode node() {
        return node;
    }
    
    @SuppressWarnings("unchecked")
    protected List<Construct> wrap(List children) {
        return wrapChildren(children);
    }
    
    private List<Construct> wrapChildren(List<ASTNode> children) {
        List<Construct> result = new ArrayList<Construct>(children.size());
        for (ASTNode child : children)
            result.add(scope.wrap(child));
        return result;
    }
    
    @Override
    public void propagateToFlow(Goal goal, ContinuationRequestor requestor) {
        for (Construct construct : controlFlow())
            requestor.propagate(construct);
    }
    
    protected final List<Construct> controlFlow() {
        List<Construct> flow = new ArrayList<Construct>();
        defineControlFlow(flow);
        return flow;
    }
    
    protected void defineControlFlow(List<Construct> flow) {
    }
    
}
