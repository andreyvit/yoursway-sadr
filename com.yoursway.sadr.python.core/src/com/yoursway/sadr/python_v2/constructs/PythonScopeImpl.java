package com.yoursway.sadr.python_v2.constructs;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public abstract class PythonScopeImpl<N extends ASTNode> extends PythonConstructImpl<N> implements Scope {
    
    public PythonScopeImpl(Scope sc, N node) {
        super(sc, node);
    }
    
    @Override
    protected void setupPrevConstructRelation(PythonConstruct prev) {
        setSyntacticallyPreviousConstruct(prev);
        prev = this;
        //scope prechildren are not taken into account.
        for (PythonConstruct construct : getPostChildren()) {
            PythonConstructImpl<ASTNode> pyConstruct = (PythonConstructImpl<ASTNode>) construct;//fixme
            pyConstruct.setupPrevConstructRelation(prev);
            prev = pyConstruct;
        }
    }
    
    @Override
    public Scope innerScope() {
        return this;
    }
    
    public List<PythonConstruct> getEnclosedConstructs() {
        List<PythonConstruct> encloseds = new LinkedList<PythonConstruct>();
        for (PythonConstruct construct : getPostChildren()) {
            addChildren(construct, encloseds);
        }
        return encloseds;
    }
    
    private void addChildren(PythonConstruct construct, List<PythonConstruct> children) {
        for (PythonConstruct child : construct.getPostChildren()) {
            if (this == child.parentScope())
                addChildren(child, children);
        }
        children.add(construct);
    }
    
    public List<PythonConstruct> getEnclosedConstructs2() {
        List<PythonConstruct> encloseds = new LinkedList<PythonConstruct>();
        for (PythonConstruct construct : getPostChildren()) {
            addChildren2(construct, encloseds);
        }
        return encloseds;
    }
    
    private void addChildren2(PythonConstruct construct, List<PythonConstruct> children) {
        children.add(construct);
        for (PythonConstruct child : construct.getPostChildren()) {
            if (this == child.parentScope())
                addChildren2(child, children);
        }
    }
    
    @Override
    public String toString() {
        return ((Scope) this).displayName();
    }
    
    abstract public String name();
    
    public PythonFileC getFileScope() {
        return parentScope().getFileScope();
    }
}
