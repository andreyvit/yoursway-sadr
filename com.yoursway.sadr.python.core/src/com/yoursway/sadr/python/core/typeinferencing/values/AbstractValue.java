package com.yoursway.sadr.python.core.typeinferencing.values;

import java.math.BigInteger;

public abstract class AbstractValue implements Value {
    
    @Override
    public String toString() {
        return this.describe();
    }
    
    public String coherseToString() {
        throw new UnsupportedOperationException();
    }
    
    public boolean cohersibleToString() {
        return false;
    }
    
    public long integerValue() {
        throw new UnsupportedOperationException();
    }
    
    public boolean isInteger() {
        return false;
    }
    
    public boolean coherseToBoolean() {
        throw new UnsupportedOperationException();
    }
    
    public boolean cohersibleToBoolean() {
        return false;
    }
    
    public BigInteger longValue() {
        throw new UnsupportedOperationException();
    }
    
    public boolean isLong() {
        return false;
    }
    
}
