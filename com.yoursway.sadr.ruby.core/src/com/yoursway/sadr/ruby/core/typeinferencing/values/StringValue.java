package com.yoursway.sadr.ruby.core.typeinferencing.values;

public class StringValue extends LiteralValue implements ValueTraits {
    
    private final String value;
    
    public StringValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return describe();
    }
    
    public String describe() {
        return "\"" + value + "\"";
    }
    
    public ValueTraits traits() {
        return this;
    }
    
    public String coherseToString() {
        return value;
    }
    
    public boolean cohersibleToString() {
        return true;
    }
    
    public long integerValue() {
        throw new UnsupportedOperationException();
    }
    
    public boolean isInteger() {
        return false;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        final StringValue other = (StringValue) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
    
    public boolean coherseToBoolean() {
        throw new UnsupportedOperationException();
    }
    
    public boolean cohersibleToBoolean() {
        return false;
    }
    
}
