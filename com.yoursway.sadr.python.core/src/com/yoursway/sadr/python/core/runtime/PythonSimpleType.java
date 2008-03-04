package com.yoursway.sadr.python.core.runtime;

public class PythonSimpleType {
    
    private final String name;
    
    public PythonSimpleType(PythonRuntimeModel model, String name) {
        this.name = name;
        model.addSimpleType(this);
    }
    
    public String name() {
        return name;
    }
    
}
