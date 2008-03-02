package com.yoursway.sadr.python.core.typeinferencing.scopes;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.runtime.RubyVariable;

public class ForCounterVariable extends RubyVariable {
    
    private final ForScope scope;
    
    public ForCounterVariable(ForScope scope) {
        this.scope = scope;
    }
    
    @Override
    public String name() {
        return "i"; // FIXME
    }
    
    public ForScope scope() {
        return scope;
    }
    
    @Override
    public String toString() {
        return name();
    }
    
    @Override
    public ASTNode[] getNodesForSelection() {
        return new ASTNode[] { scope.node() };
    }
    
}
