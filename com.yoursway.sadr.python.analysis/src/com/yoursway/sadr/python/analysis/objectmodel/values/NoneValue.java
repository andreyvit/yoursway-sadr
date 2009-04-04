package com.yoursway.sadr.python.analysis.objectmodel.values;

import com.yoursway.sadr.python.analysis.objectmodel.types.NoneType;
import com.yoursway.sadr.python.analysis.objectmodel.types.PythonType;

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
