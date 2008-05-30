package com.yoursway.sadr.python.core.typeinferencing.values;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;

public class BooleanValue extends AbstractValue {
    
    private final boolean value;
    public static final String TRUE = "True";
    public static final String FALSE = "False";
    
    public BooleanValue(boolean value) {
        this.value = value;
    }
    
    @Override
    public int hashCode() {
        return new Boolean(value).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BooleanValue))
            return false;
        return ((BooleanValue) obj).value == this.value;
    }
    
    public String describe() {
        return value ? TRUE : FALSE;
    }
    
    public BooleanValue and(BooleanValue rhs) {
        return new BooleanValue(this.value && rhs.value);
    }
    
    public BooleanValue or(BooleanValue rhs) {
        return new BooleanValue(this.value || rhs.value);
    }
    
    public BooleanValue xor(BooleanValue rhs) {
        return new BooleanValue(this.value ^ rhs.value);
    }
    
    public BooleanValue not() {
        return new BooleanValue(!this.value);
    }
}
