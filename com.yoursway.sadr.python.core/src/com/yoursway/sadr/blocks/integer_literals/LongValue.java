package com.yoursway.sadr.blocks.integer_literals;

import java.math.BigInteger;

import com.yoursway.sadr.python.core.typeinferencing.values.LiteralValue;
import com.yoursway.sadr.python.core.typeinferencing.values.ValueTraits;

public class LongValue extends LiteralValue implements ValueTraits {
    
    private final BigInteger value;
    
    public LongValue(BigInteger v) {
        this.value = v;
    }
    
    public BigInteger value() {
        return value;
    }
    
    @Override
    public String toString() {
        return describe();
    }
    
    public String describe() {
        return value.toString();
    }
    
    public ValueTraits traits() {
        return this;
    }
    
    public String coherseToString() {
        return value.toString();
    }
    
    public boolean cohersibleToString() {
        return true;
    }
    
    public BigInteger longValue() {
        return value;
    }
    
    public boolean isLong() {
        return true;
    }
    
    public long integerValue() {
        return 0;
    }
    
    public boolean isInteger() {
        return false;
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final LongValue other = (LongValue) obj;
        return (value.equals(other.value));
    }
    
    public boolean coherseToBoolean() {
        return (value.signum() != 0);
    }
    
    public boolean cohersibleToBoolean() {
        return true;
    }
}
