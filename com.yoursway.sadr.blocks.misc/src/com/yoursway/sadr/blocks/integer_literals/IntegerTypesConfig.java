package com.yoursway.sadr.blocks.integer_literals;

public class IntegerTypesConfig {
    
    final String intTypeName;
    final String longTypeName;
    
    public IntegerTypesConfig(String intTypeName, String longTypeName) {
        if (intTypeName == null)
            throw new NullPointerException("intTypeName is null");
        if (longTypeName == null)
            throw new NullPointerException("longTypeName is null");
        this.intTypeName = intTypeName;
        this.longTypeName = longTypeName;
    }
    
}
