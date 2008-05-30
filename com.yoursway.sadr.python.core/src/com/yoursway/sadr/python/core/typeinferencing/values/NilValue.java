package com.yoursway.sadr.python.core.typeinferencing.values;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;

public class NilValue extends AbstractValue {
    
    public NilValue() {
    }
    
    private static final NilValue instance = new NilValue();
    
    public static NilValue instance() {
        return instance;
    }
    
    public String describe() {
        return "None";
    }
}
