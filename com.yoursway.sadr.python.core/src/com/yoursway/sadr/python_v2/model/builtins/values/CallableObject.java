package com.yoursway.sadr.python_v2.model.builtins.values;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;

public interface CallableObject {
    
    public abstract PythonValueSet call(Krocodile crocodile, RuntimeArguments args);
    
    public abstract String name();
}
