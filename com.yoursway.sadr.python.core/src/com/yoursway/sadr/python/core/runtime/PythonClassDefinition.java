package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

public abstract class PythonClassDefinition {
    
    private final PythonClass klass;
    
    public PythonClassDefinition(PythonClass klass) {
        this.klass = klass;
        klass.addClassDefinition(this);
    }
    
    public PythonClass klass() {
        return klass;
    }
    
    public abstract PythonClass superclass();
    
    public abstract ASTNode nodeForSelection();
    
}
