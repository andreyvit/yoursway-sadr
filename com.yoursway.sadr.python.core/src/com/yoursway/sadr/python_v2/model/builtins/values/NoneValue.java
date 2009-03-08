package com.yoursway.sadr.python_v2.model.builtins.values;

import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.types.NoneType;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonType;

public class NoneValue extends PythonValue {
    
    public NoneValue() {
    }
    
    public static final NoneValue instance = new NoneValue();
    
    @Override
    public String describe() {
        return "None";
    }
    
    @Override
    public PythonType getType() {
        return NoneType.instance();
    }
}
