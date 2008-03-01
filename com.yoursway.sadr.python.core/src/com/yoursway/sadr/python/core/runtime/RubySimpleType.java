package com.yoursway.sadr.python.core.runtime;

public class RubySimpleType {
    
    private final String name;
    
    public RubySimpleType(RubyRuntimeModel model, String name) {
        this.name = name;
        model.addSimpleType(this);
    }
    
    public String name() {
        return name;
    }
    
}
