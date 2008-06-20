package com.yoursway.sadr.blocks.dedicated_calls;

class SimpleTypeImpl implements SimpleType {
    
    private final String name;
    
    public SimpleTypeImpl(RuntimeModelWithSimpleTypesImpl model, String name) {
        if (name == null)
            throw new NullPointerException("name is null");
        this.name = name;
        model.addSimpleType(this);
    }
    
    public String name() {
        return name;
    }
    
}
