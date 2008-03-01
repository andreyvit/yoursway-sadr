package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ruby.ast.RubyAssignment;
import org.eclipse.dltk.ruby.ast.RubyForStatement2;

import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.runtime.contributions.ContributableItem;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class RubyLocalVariable extends RubyVariable implements ContributableItem {
    
    private final ASTNode node;
    private final LocalVariableContainer container;
    private final Scope scope;
    private final String name;
    
    public RubyLocalVariable(LocalVariableContainer container, Context context, Scope scope,
            RubyAssignment node1) {
        this.container = container;
        this.scope = scope;
        SimpleReference left = (SimpleReference) node1.getLeft();
        this.node = node1.getLeft();
        this.name = (left).getName();
        container.addLocalVariable(this);
        context.add(this);
    }
    
    public RubyLocalVariable(LocalVariableContainer container, Context context, Scope scope,
            RubyForStatement2 node) {
        this.container = container;
        this.scope = scope;
        this.node = node;
        this.name = ((SimpleReference) ((RubyAssignment) node.getTarget()).getLeft()).getName();
        if (name == null)
            throw new NullPointerException("name == null");
        container.addLocalVariable(this);
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
        return new ASTNode[] { node };
    }
    
}
