package com.yoursway.sadr.blocks.simple_types;

import com.yoursway.sadr.blocks.foundation.RuntimeModel;

public class PythonSimpleType {
    
    private final String name;
    
    public PythonSimpleType(RuntimeModel model, String name) {
        this.name = name;
        model.get(SimpleTypeModelFacelet.class).addSimpleType(this);
    }
    
    public String name() {
        return name;
    }
    
}
