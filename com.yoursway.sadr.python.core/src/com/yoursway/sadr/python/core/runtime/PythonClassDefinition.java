package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

public abstract class PythonClassDefinition {
    
    private final PythonMetaType klass;
    
    public PythonClassDefinition(PythonMetaType klass) {
        this.klass = klass;
        klass.addClassDefinition(this);
    }
    
    public PythonMetaType klass() {
        return klass;
    }
    
    public abstract PythonMetaType superclass();
    
    public abstract ASTNode nodeForSelection();
    
}
