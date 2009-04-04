package com.yoursway.sadr.python.analysis.lang.constructs;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.lexical.PythonScope;

public interface CallableDeclaration extends PythonScope, PythonConstruct {
    
    String name();
    
    PythonConstruct getArgInit(String name);
    
    @pausable
    Collection<PythonConstruct> findReturnedValues();
    
    DeclaredArguments getDeclaredArguments();
    
}
