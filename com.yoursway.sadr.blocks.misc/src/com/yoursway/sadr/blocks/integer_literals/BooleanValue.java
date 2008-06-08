package com.yoursway.sadr.blocks.integer_literals;

import java.math.BigInteger;


public class BooleanValue extends NumericValue {
    
    private final boolean value;
    public static final String TRUE = "True";
    public static final String FALSE = "False";
    
    public BooleanValue(boolean value) {
        this.value = value;
    }
    
    @Override
    public int hashCode() {
        return new Boolean(value).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BooleanValue))
            return false;
        return ((BooleanValue) obj).value == this.value;
    }
    
    @Override
    public String describe() {
        return value ? TRUE : FALSE;
    }
    
    public BooleanValue not() {
        return new BooleanValue(!this.value);
    }
    
    @Override
    public boolean coercibleToInt() {
        return true;
    }
    
    @Override
    public long coerceToInt() {
        return value ? 1 : 0;
    }
    
    @Override
    public BigInteger coerceToLong() {
        return value ? BigInteger.ONE : BigInteger.ZERO;
    }
    
    @Override
    public double coerceToFloat() {
        return value ? 1 : 0;
    }

	@Override
	public boolean coerceToBool() {
		return value;
	}
}
