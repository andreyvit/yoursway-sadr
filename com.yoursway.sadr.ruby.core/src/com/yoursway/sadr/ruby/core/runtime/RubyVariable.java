package com.yoursway.sadr.ruby.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

public abstract class RubyVariable {
    
    @Override
    public abstract String toString();
    
    public abstract ASTNode[] getNodesForSelection();
    
    public abstract String name();
    
}
