package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public interface PythonCallable extends PythonConstruct {
    String name();
    
    PythonValueSet call(Krocodile crocodile, PythonArguments args);
}
