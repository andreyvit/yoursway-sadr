package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.goals.Acceptor;

public abstract class PythonVariableAcceptor extends Acceptor {
    public PythonVariableAcceptor() {
    }
    
    public abstract void addResult(String key, RuntimeObject value);
}
