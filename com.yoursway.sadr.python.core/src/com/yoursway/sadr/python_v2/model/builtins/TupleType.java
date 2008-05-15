package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class TupleType extends PythonClassType {
    private TupleType() {
    }
    
    private static final TupleType instance = new TupleType();
    
    public static TupleType instance() {
        return instance;
    }
    
    @Override
    public String describe() {
        return "tuple";
    }
    
    public static PythonObjectWithValue<TupleValue> newTupleObject(List<RuntimeObject> list) {
        return new PythonObjectWithValue<TupleValue>(instance(), new TupleValue(list));
    }
    
}
