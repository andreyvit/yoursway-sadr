package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

public class PythonObjectWithValue extends PythonObject implements RuntimeObjectWithValue {
    
    private final Value value;
    
    public PythonObjectWithValue(PythonClass type, Value value, PythonConstruct declaration) {
        super(type, declaration);
        this.value = value;
    }
    
    public Value getValue() {
        return value;
    }
    
    @Override
    public String describe() {
        return value.describe();
    }
}
