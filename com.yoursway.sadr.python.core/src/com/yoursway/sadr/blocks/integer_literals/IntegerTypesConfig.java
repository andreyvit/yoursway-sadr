package com.yoursway.sadr.blocks.integer_literals;

public class IntegerTypesConfig {
    
    private final String intTypeName;
    private final String longTypeName;
    
    public IntegerTypesConfig(String intTypeName, String longTypeName) {
        this.intTypeName = intTypeName;
        this.longTypeName = longTypeName;
    }
    
    public String getIntTypeName() {
        return intTypeName;
    }
    
    public String getLongTypeName() {
        return longTypeName;
    }
    
}
