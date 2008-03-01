package com.yoursway.sadr.ruby.core.typeinferencing.values;

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
    
    public String coherseToString() {
        return Long.toString(value);
    }
    
    public boolean cohersibleToString() {
        return true;
    }
    
    public long integerValue() {
        return value;
    }
    
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
    
    public boolean coherseToBoolean() {
        return (value != 0);
    }
    
    public boolean cohersibleToBoolean() {
        return true;
    }
    
}
