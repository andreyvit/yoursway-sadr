package com.yoursway.sadr.python.model.values;

import com.yoursway.sadr.python.objects.RuntimeArguments;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public interface CallableObject {
    
    public abstract PythonValueSet call(PythonDynamicContext crocodile, RuntimeArguments args);
    
    public abstract String name();
}
