package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

public class PythonBuiltinClassDefinition extends PythonClassDefinition {
    
    private final PythonClass superclass;
    
    public PythonBuiltinClassDefinition(PythonClass klass, PythonClass superclass) {
        super(klass);
        this.superclass = superclass;
    }
    
    @Override
    public ASTNode nodeForSelection() {
        return null;
    }
    
    @Override
    public PythonClass superclass() {
        return superclass;
    }
    
}
