package com.yoursway.sadr.python_v2.model.builtins.values;

import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonType;

public class InstanceValue extends PythonObject {
    
    private final PythonType type;
    
    public InstanceValue(PythonType receiverType) {
        this.type = receiverType;
    }
    
    @Override
    public PythonType getType() {
        return type;
    }
    
}
