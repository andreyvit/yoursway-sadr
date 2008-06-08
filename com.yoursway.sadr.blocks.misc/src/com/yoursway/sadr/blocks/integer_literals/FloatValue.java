package com.yoursway.sadr.blocks.integer_literals;

import java.math.BigDecimal;
import java.math.BigInteger;


public class FloatValue extends NumericValue {

    private final double value;
    
    public FloatValue(double v) {
        this.value = v;
    }
    
    public FloatValue(long val) {
        this.value = Double.valueOf(val);
	}

	public double value() {
        return value;
    }
    
    public String describe() {
    	return String.valueOf(value);
        //return String.format("%.16g", value);
    }
    
    public boolean coercibleToInt(){
    	return true;
    }

    public long coerceToInt(){
    	return (int) value;
    }

	@Override
	public BigInteger coerceToLong() {
		return BigDecimal.valueOf(value).toBigInteger();
	}

	@Override
	public double coerceToFloat() {
		return this.value();
	}

	@Override
	public boolean coerceToBool() {
		return this.value() != 0;
	}
}
