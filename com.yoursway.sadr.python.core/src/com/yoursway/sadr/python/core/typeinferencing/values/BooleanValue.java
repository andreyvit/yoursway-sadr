package com.yoursway.sadr.python.core.typeinferencing.values;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.ValueTraits;

public class BooleanValue extends AbstractValue implements ValueTraits {
    
    private final boolean value;
    public static final String TRUE = "True";
    public static final String FALSE = "False";
    
    public BooleanValue(boolean value) {
        this.value = value;
    }
    
    @Override
    public int hashCode() {
        return (value ? TRUE : FALSE).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BooleanValue))
            return false;
        return ((BooleanValue) obj).value == this.value;
    }
    
    @Override
    public String toString() {
        return value ? TRUE : FALSE;
    }
    
    public String describe() {
        return value ? TRUE : FALSE;
    }
    
    public ValueTraits traits() {
        return this;
    }
    
    public BooleanValue and(BooleanValue value2) {
        return new BooleanValue(this.value && value2.value);
    }
    
    public BooleanValue or(BooleanValue value2) {
        return new BooleanValue(this.value || value2.value);
    }
    
    public BooleanValue xor(BooleanValue value2) {
        return new BooleanValue(this.value ^ value2.value);
    }
}
