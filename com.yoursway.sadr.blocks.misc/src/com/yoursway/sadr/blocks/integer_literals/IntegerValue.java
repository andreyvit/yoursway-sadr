package com.yoursway.sadr.blocks.integer_literals;

import java.math.BigInteger;

public class IntegerValue extends NumericValue{
    
    private final long value;
    
    public IntegerValue(long value) {
        this.value = value;
    }
    
    public long value() {
        return value;
    }
    
    public String describe() {
        return Long.toString(value);
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

    private NumericValue expand(long res) {
    	return isInt(res) ? new IntegerValue(res) : new LongValue(res);
    }
    
    public boolean isInt(long res) {
    	return res<=Integer.MAX_VALUE && res>=Integer.MIN_VALUE;
    }
    
    public NumericValue add(NumericValue rhs) {
    	if(rhs instanceof IntegerValue)
			return expand(this.value() + ((IntegerValue)rhs).value());
		else
			return super.add(rhs);
    }

	public NumericValue subtract(NumericValue rhs) {
    	if(rhs instanceof IntegerValue)
    		return expand(this.value() - ((IntegerValue)rhs).value());
		else
			return super.subtract(rhs);
	}

	public NumericValue multiply(NumericValue rhs) {
    	if(rhs instanceof IntegerValue)
    		return expand(this.value() * ((IntegerValue)rhs).value());
		else
			return super.multiply(rhs);
	}

	public NumericValue divide(NumericValue rhs) {
    	if(rhs instanceof IntegerValue)
    		return expand(this.value() / ((IntegerValue)rhs).value());
		else
			return super.divide(rhs);
	}

	@Override
	public BigInteger coerceToLong() {
		return BigInteger.valueOf(value());
	}

	@Override
	public long coerceToInt() {
		return value();
	}

	@Override
	public boolean coercibleToInt() {
		return true;
	}

	@Override
	public double coerceToFloat() {
		return value();
	}

	@Override
	public boolean coerceToBool() {
		return value() != 0;
	}
}
