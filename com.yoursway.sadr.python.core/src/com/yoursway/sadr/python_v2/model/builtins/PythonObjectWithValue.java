package com.yoursway.sadr.python_v2.model.builtins;

public class PythonObjectWithValue<ValueType> extends PythonObject implements
        RuntimeObjectWithValue<ValueType> {
    
    private final ValueType value;
    
    public PythonObjectWithValue(PythonClass type, ValueType value) {
        super(type);
        this.value = value;
    }
    
    public ValueType getValue() {
        return value;
    }
    
}
