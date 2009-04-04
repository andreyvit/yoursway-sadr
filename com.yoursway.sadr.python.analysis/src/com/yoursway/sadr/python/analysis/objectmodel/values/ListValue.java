package com.yoursway.sadr.python.analysis.objectmodel.values;

import com.yoursway.sadr.python.analysis.objectmodel.types.ListType;
import com.yoursway.sadr.python.analysis.objectmodel.types.PythonType;

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
