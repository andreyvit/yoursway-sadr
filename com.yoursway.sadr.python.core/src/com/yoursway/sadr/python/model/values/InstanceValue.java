package com.yoursway.sadr.python.model.values;

import com.yoursway.sadr.python.model.types.PythonType;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class InstanceValue extends PythonValue {
    
    private final PythonType type;
    
    public InstanceValue(PythonType receiverType) {
        this.type = receiverType;
    }
    
    @Override
    public PythonType getType() {
        return type;
    }
    
    @Override
    public String describe() {
        return "<" + getType().describe() + ">";
    }
    
}
