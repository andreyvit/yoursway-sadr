package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.python_v2.goals.Acceptor;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

public abstract class PythonVariableAcceptor extends Acceptor {
    public PythonVariableAcceptor() {
    }
    
    public abstract void addResult(String key, PythonObject value);
}
