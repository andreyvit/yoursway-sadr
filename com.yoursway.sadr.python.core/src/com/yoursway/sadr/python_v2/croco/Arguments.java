package com.yoursway.sadr.python_v2.croco;

import kilim.pausable;

import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public interface Arguments {
    
    @pausable
    public abstract PythonValueSet computeArgument(PythonDynamicContext dc, int index, String name,
            PythonConstruct init);
    
}