package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

public class PythonBuiltinClassDefinition extends PythonClassDefinition {
    
    private final PythonClassType superclass;
    
    public PythonBuiltinClassDefinition(PythonClassType klass, PythonClassType superclass) {
        super(klass);
        this.superclass = superclass;
    }
    
    @Override
    public ASTNode nodeForSelection() {
        return null;
    }
    
    @Override
    public PythonClassType superclass() {
        return superclass;
    }
    
}
