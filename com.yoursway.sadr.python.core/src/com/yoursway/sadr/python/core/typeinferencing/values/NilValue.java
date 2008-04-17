package com.yoursway.sadr.python.core.typeinferencing.values;

import java.math.BigInteger;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.ValueTraits;

public class NilValue extends AbstractValue implements ValueTraits {
    
    public NilValue() {
    }
    
    @Override
    public String toString() {
        return describe();
    }
    
    public String describe() {
        return "nil";
    }
    
    public ValueTraits traits() {
        return this;
    }
    
    @Override
    public String coherseToString() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean cohersibleToString() {
        return false;
    }
    
    @Override
    public long integerValue() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean isInteger() {
        return false;
    }
    
    @Override
    public boolean coherseToBoolean() {
        return false;
    }
    
    @Override
    public boolean cohersibleToBoolean() {
        return true;
    }
    
    @Override
    public boolean isLong() {
        return false;
    }
    
    @Override
    public BigInteger longValue() {
        return null;
    }
    
}
