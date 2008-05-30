package com.yoursway.sadr.python_v2.constructs;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public abstract class PythonScopeImpl<N extends ASTNode> extends PythonConstructImpl<N> implements Scope {
    
    public PythonScopeImpl(Scope sc, N node) {
        super(sc, node);
        wrapEnclosedChildren();
    }
    
    @Override
    public Scope innerScope() {
        return this;
    }
    
    @Override
    protected void wrapEnclosedChildren() {
        setChildConstructs(PythonConstructFactory.wrap(this.node.getChilds(), this));
    }
    
    public List<PythonConstruct> getEnclosedConstructs() {
        List<PythonConstruct> encloseds = new LinkedList<PythonConstruct>();
        for (PythonConstruct construct : getChildConstructs()) {
            addChildren(construct, encloseds);
        }
        return encloseds;
    }
    
    private void addChildren(PythonConstruct construct, List<PythonConstruct> children) {
        for (PythonConstruct child : construct.getChildConstructs()) {
            if (this == child.parentScope())
                addChildren(child, children);
        }
        children.add(construct);
    }
    
    public List<PythonConstruct> getEnclosedConstructs2() {
        List<PythonConstruct> encloseds = new LinkedList<PythonConstruct>();
        for (PythonConstruct construct : getChildConstructs()) {
            addChildren2(construct, encloseds);
        }
        return encloseds;
    }
    
    private void addChildren2(PythonConstruct construct, List<PythonConstruct> children) {
        children.add(construct);
        for (PythonConstruct child : construct.getChildConstructs()) {
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
