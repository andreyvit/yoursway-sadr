package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

public abstract class PythonClassDefinition {
    
    private final PythonClassType klass;
    
    public PythonClassDefinition(PythonClassType klass) {
        this.klass = klass;
        klass.addClassDefinition(this);
    }
    
    public PythonClassType klass() {
        return klass;
    }
    
    public abstract PythonClassType superclass();
    
    public abstract ASTNode nodeForSelection();
    
}
