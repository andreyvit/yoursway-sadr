package com.yoursway.sadr.python.core.typeinferencing.values;

import java.math.BigInteger;

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
    public boolean cohersibleToBoolean() {
        return true;
    }
    
    @Override
    public boolean coherseToBoolean() {
        return this.value;
    }
    
    @Override
    public boolean cohersibleToString() {
        return true;
    }
    
    @Override
    public String coherseToString() {
        return value ? TRUE : FALSE;
    }
    
    @Override
    public long integerValue() {
        return value ? 1 : 0;
    }
    
    @Override
    public boolean isInteger() {
        return false;
    }
    
    @Override
    public BigInteger longValue() {
        return BigInteger.valueOf(value ? 1 : 0);
    }
    
    @Override
    public boolean isLong() {
        return false;
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
}
