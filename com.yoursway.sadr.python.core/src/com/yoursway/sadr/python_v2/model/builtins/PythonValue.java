package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

public abstract class PythonValue extends PythonObject implements Value {
    public PythonValue(PythonConstruct declaration) {
        super(declaration);
    }
    
    public PythonValue() {
    }
    
    public PythonValue getValue() {
        return this;
    }
}
