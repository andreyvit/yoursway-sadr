package com.yoursway.sadr.python.model.types;

public class NoneType extends PythonType {
    NoneType() {
        super();
    }
    
    private static NoneType instance = new NoneType();
    
    public static NoneType instance() {
        return instance;
    }
    
    @Override
    public String describe() {
        return "NoneType";
    }
}
