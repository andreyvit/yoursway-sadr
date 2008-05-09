package com.yoursway.sadr.blocks.integer_literals;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.ValueTraits;

public class IntegerValue extends AbstractValue implements ValueTraits {
    
    private final long value;
    
    public IntegerValue(long value) {
        this.value = value;
    }
    
    public long value() {
        return value;
    }
    
    public IntegerValue add(IntegerValue val) {
    	return new IntegerValue(this.value() + val.value());
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

	public IntegerValue subtract(IntegerValue value2) {
		return new IntegerValue(this.value() - value2.value());
	}
}
