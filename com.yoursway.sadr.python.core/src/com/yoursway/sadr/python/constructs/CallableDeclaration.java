package com.yoursway.sadr.python.constructs;

import java.util.Collection;
import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.PythonArgument;

public interface CallableDeclaration extends PythonConstruct {
    List<PythonArgument> getArguments();
    
    String name();
    
    PythonConstruct getArgInit(String name);
    
    @pausable
    Collection<PythonConstruct> findReturnedValues();
    
}
