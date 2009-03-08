package com.yoursway.sadr.python_v2.model.builtins.types;

import com.yoursway.sadr.blocks.foundation.types.Type;

public class StubType implements Type {
    
    public static final Type WILDCARD = new StubType("WILDCARD");
    
    private final String name;
    
    public StubType(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public String describe() {
        return name;
    }
    
}
