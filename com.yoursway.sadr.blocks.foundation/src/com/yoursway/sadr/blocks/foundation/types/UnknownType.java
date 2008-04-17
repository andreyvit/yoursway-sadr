package com.yoursway.sadr.blocks.foundation.types;

public class UnknownType implements Type {
    
    public static final UnknownType INSTANCE = new UnknownType();
    
    private UnknownType() {
    }
    
    public String describe() {
        return "?";
    }
    
    @Override
    public String toString() {
        return describe();
    }
    
}
