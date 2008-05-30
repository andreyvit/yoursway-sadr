package com.yoursway.sadr.blocks.integer_literals;

import java.math.BigInteger;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;

public class LongValue extends AbstractValue {
    
    private final BigInteger value;
    
    public LongValue(BigInteger v) {
        this.value = v;
    }
    
    public BigInteger value() {
        return value;
    }
    
    public String describe() {
        return value.toString();
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

    public LongValue add(LongValue val) {
    	return new LongValue(this.value().add(val.value()));
    }
    
    public LongValue subtract(LongValue val) {
    	return new LongValue(this.value().subtract(val.value()));
    }
    
    public LongValue multiply(LongValue val) {
    	return new LongValue(this.value().multiply(val.value()));
    }
    
    public LongValue divide(LongValue val) {
    	return new LongValue(this.value().divide(val.value()));
    }
    
}
