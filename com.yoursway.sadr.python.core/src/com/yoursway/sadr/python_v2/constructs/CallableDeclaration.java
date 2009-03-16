package com.yoursway.sadr.python_v2.constructs;

import java.util.List;

import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public interface CallableDeclaration extends PythonConstruct {
    List<PythonArgument> getArguments();
    
    String name();
    
    PythonConstruct getArgInit(String name);
    
    PythonValueSet call(Krocodile crocodile);
}
