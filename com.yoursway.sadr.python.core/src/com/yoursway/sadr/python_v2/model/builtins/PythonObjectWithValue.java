package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class PythonObjectWithValue<ValueType extends Value> extends PythonObject implements
        RuntimeObjectWithValue<ValueType> {
    
    private final ValueType value;
    
    public PythonObjectWithValue(PythonClassType type, ValueType value, PythonConstruct declaration) {
        super(type, declaration);
        this.value = value;
    }
    
    public PythonObjectWithValue(PythonClassType type, ValueType value) {
        super(type);
        this.value = value;
    }
    
    @Override
    public RuntimeObject getAttribute(String name) {
        RuntimeObject attribute = super.getAttribute(name);
        if (attribute == null) {
            attribute = this.getType().getAttribute(name);
        }
        return attribute;
    }
    
    public ValueType getValue() {
        return value;
    }
    
    @Override
    public String describe() {
        return value.describe();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> T convertValue(RuntimeObject type) {
        if (!getType().equals(type)) {
            return null;
        }
        Object value = getValue();
        return (T) value;
    }
}
