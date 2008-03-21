package com.yoursway.sadr.python.core.typeinferencing.values;

public class BooleanValue extends LiteralValue implements ValueTraits {
    
    private final boolean value;
    public static final String TRUE = "True";
    public static final String FALSE = "False";
    
    public BooleanValue(boolean value) {
        this.value = value;
    }
    
    public boolean coherseToBoolean() {
        // TODO Auto-generated method stub
        return false;
    }
    
    public String coherseToString() {
        return value ? TRUE : FALSE;
    }
    
    public boolean cohersibleToBoolean() {
        return true;
    }
    
    public boolean cohersibleToString() {
        return true;
    }
    
    public long integerValue() {
        return value ? 1 : 0;
    }
    
    public boolean isInteger() {
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
