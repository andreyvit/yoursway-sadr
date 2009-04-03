package com.yoursway.sadr.python.model.values;

import com.yoursway.sadr.python.model.types.ListType;
import com.yoursway.sadr.python.model.types.PythonType;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class ListValue extends PythonValue {
    
    public ListValue() {
    }
    
    @Override
    public String describe() {
        return "[]";
    }
    
    @Override
    public PythonType getType() {
        return ListType.instance;
    }
    
}
