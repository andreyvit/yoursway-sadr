package com.yoursway.sadr.python.core.typeinferencing.values;

public class NilValue extends LiteralValue implements ValueTraits {
    
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
        return false;
    }
    
    public boolean cohersibleToBoolean() {
        return true;
    }
    
}
