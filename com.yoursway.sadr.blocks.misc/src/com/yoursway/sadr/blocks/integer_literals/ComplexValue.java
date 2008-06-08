package com.yoursway.sadr.blocks.integer_literals;

import java.math.BigInteger;

public class ComplexValue extends NumericValue {

	private Complex value;

    public ComplexValue(Complex v) {
        this.value = v;
    }
    
	public Complex value() {
        return value;
    }
    
    public String describe() {
        return value.toString();
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
		ComplexValue other = (ComplexValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
    
    public boolean coercibleToInt(){
    	return false;
    }

    public long coerceToInt(){
    	throw new IllegalStateException();
    }

	@Override
	public BigInteger coerceToLong() {
    	throw new IllegalStateException();
	}

	@Override
	public double coerceToFloat() {
    	throw new IllegalStateException();
	}

	public Complex coerceToComplex() {
		return value();
	}

	@Override
	public boolean coerceToBool() {
		return !value.isZero();
	}
}
