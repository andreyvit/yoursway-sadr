package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.succeeder.IAcceptor;

public abstract class PythonVariableAcceptor implements IAcceptor {
    
    public abstract void addResult(String key, RuntimeObject value);
}
