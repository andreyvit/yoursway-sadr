package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

public class PythonObjectWithValue<ValueType extends Value> extends PythonObject implements
        RuntimeObjectWithValue<ValueType> {
    
    private final ValueType value;
    
    public PythonObjectWithValue(PythonClass type, ValueType value, PythonConstruct declaration) {
        super(type, declaration);
        this.value = value;
    }
    
    public ValueType getValue() {
        return value;
    }
    
    @Override
    public String describe() {
        return value.describe();
    }
}
