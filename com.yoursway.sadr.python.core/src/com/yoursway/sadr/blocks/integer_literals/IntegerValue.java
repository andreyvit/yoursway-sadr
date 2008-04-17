package com.yoursway.sadr.blocks.integer_literals;

import java.math.BigInteger;

import com.yoursway.sadr.python.core.typeinferencing.values.LiteralValue;
import com.yoursway.sadr.python.core.typeinferencing.values.ValueTraits;

public class IntegerValue extends LiteralValue implements ValueTraits {
    
    private final long value;
    
    public IntegerValue(long value) {
        this.value = value;
    }
    
    public long value() {
        return value;
    }
    
    @Override
    public String toString() {
        return describe();
    }
    
    public String describe() {
        return Long.toString(value);
    }
    
    public ValueTraits traits() {
        return this;
    }
    
    @Override
    public String coherseToString() {
        return Long.toString(value);
    }
    
    @Override
    public boolean cohersibleToString() {
        return true;
    }
    
    @Override
    public long integerValue() {
        return value;
    }
    
    @Override
    public boolean isInteger() {
        return true;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (value ^ (value >>> 32));
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final IntegerValue other = (IntegerValue) obj;
        if (value != other.value)
            return false;
        return true;
    }
    
    @Override
    public boolean coherseToBoolean() {
        return (value != 0);
    }
    
    @Override
    public boolean cohersibleToBoolean() {
        return true;
    }
    
    @Override
    public boolean isLong() {
        return true;
    }
    
    @Override
    public BigInteger longValue() {
        return BigInteger.valueOf(value);
    }
    
}
