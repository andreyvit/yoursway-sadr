package com.yoursway.sadr.python.constructs;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.python_v2.croco.DeclaredArguments;

public interface CallableDeclaration extends PythonScope, PythonConstruct {
    
    String name();
    
    PythonConstruct getArgInit(String name);
    
    @pausable
    Collection<PythonConstruct> findReturnedValues();
    
    DeclaredArguments getDeclaredArguments();
    
}
