package com.yoursway.sadr.python.core.typeinferencing.values;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;

public class NilValue extends AbstractValue {
    
    public NilValue() {
    }
    
    @Override
    public String toString() {
        return describe();
    }
    
    public String describe() {
        return "None";
    }
}
