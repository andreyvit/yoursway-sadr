package com.yoursway.sadr.blocks.integer_literals;

import java.math.BigInteger;

public class LongValue extends NumericValue {

    private final BigInteger value;
    
    public LongValue(BigInteger v) {
        this.value = v;
    }
    
    public LongValue(long val) {
        this.value = BigInteger.valueOf(val);
	}

	public BigInteger value() {
        return value;
    }
    
    public String describe() {
        return value.toString()+"L";
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
    
    public boolean coercibleToInt(){
    	return value().bitLength()<32;
    }

    public long coerceToInt(){
    	return value().longValue();
    }

	@Override
	public BigInteger coerceToLong() {
		return value();
	}
}
