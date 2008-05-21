package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

public class PythonBuiltinClassDefinition extends PythonClassDefinition {
    
    private final PythonMetaType superclass;
    
    public PythonBuiltinClassDefinition(PythonMetaType klass, PythonMetaType superclass) {
        super(klass);
        this.superclass = superclass;
    }
    
    @Override
    public ASTNode nodeForSelection() {
        return null;
    }
    
    @Override
    public PythonMetaType superclass() {
        return superclass;
    }
    
}
