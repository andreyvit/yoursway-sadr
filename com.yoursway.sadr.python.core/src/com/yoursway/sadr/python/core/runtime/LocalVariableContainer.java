package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

public interface LocalVariableContainer {
    
    void addLocalVariable(PythonLocalVariable localVariable);
    
    ASTNode node();
    
}
