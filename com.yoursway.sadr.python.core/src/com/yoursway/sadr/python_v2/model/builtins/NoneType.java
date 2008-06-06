package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python.core.typeinferencing.values.NilValue;

public class NoneType extends PythonClassType {
    private NoneType() {
    }
    
    private static NoneType instance = new NoneType();
    
    public static NoneType instance() {
        return instance;
    }
    
    public static PythonValue<NilValue> wrap(NilValue value) {
        return new PythonValue<NilValue>(instance(), value);
    }
    
    @Override
    public String describe() {
        return "NoneType";
    }
}
