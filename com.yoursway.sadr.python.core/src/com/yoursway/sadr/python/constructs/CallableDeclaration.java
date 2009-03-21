package com.yoursway.sadr.python.constructs;

import java.util.Collection;

import kilim.pausable;

public interface CallableDeclaration extends PythonConstruct {
    
    String name();
    
    PythonConstruct getArgInit(String name);
    
    @pausable
    Collection<PythonConstruct> findReturnedValues();
    
}
