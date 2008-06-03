package com.yoursway.sadr.python.core.typeinferencing.values;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;

public class UnicodeValue extends AbstractValue {
    
    private final String value;
    
    public UnicodeValue(String value) {
        this.value = value;
    }
    
    @Override
    public String describe() {
        return "u\'" + value + "\'";
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
        final UnicodeValue other = (UnicodeValue) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
    
    public UnicodeValue add(UnicodeValue val) {
        return new UnicodeValue(this.value() + val.value());
    }
    
    public String value() {
        return value;
    }
    
    public UnicodeValue format(UnicodeValue stringValue) {
        //FIXME
        return new UnicodeValue(this.value() + stringValue.value());
    }
}
