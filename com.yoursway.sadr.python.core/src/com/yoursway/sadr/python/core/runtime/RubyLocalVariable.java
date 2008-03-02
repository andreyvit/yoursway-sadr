package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.runtime.contributions.ContributableItem;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class RubyLocalVariable extends RubyVariable implements ContributableItem {
    
    private final LocalVariableContainer container;
    private final Scope scope;
    private final String name;
    
    public RubyLocalVariable(LocalVariableContainer container, Context context, Scope scope, String name) {
        this.container = container;
        this.scope = scope;
        this.name = name;
        container.addLocalVariable(this);
        if (context != null)
            context.add(this);
    }
    
    public LocalVariableContainer container() {
        return container;
    }
    
    public Scope scope() {
        return scope;
    }
    
    @Override
    public String name() {
        return name;
    }
    
    @Override
    public String toString() {
        return container + "." + name();
    }
    
    @Override
    public ASTNode[] getNodesForSelection() {
        return new ASTNode[] {};
    }
    
}
