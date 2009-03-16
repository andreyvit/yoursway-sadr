package com.yoursway.sadr.python.model.values;

import com.yoursway.sadr.python.model.types.NoneType;
import com.yoursway.sadr.python.model.types.PythonType;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

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
