package com.yoursway.sadr.python.core.typeinferencing.constructs;

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
        children.add(construct);
        for (PythonConstruct child : construct.getChildConstructs()) {
            if (this == child.parentScope())
                addChildren(child, children);
        }
    }
    
    @Override
    public String toString() {
        return ((Scope) this).displayName();
    }
}
