package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

public abstract class PythonVariable {
    
    @Override
    public abstract String toString();
    
    public abstract ASTNode[] getNodesForSelection();
    
    public abstract String name();
    
}