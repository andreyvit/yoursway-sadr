package com.yoursway.sadr.blocks.integer_literals;

import java.math.BigInteger;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;

public abstract class NumericValue extends AbstractValue{
	
	public NumericValue add(NumericValue rhs){
		return new LongValue(this.coerceToLong().add(rhs.coerceToLong()));
	}
	public NumericValue subtract(NumericValue rhs){
		return new LongValue(this.coerceToLong().subtract(rhs.coerceToLong()));
	}
	public NumericValue multiply(NumericValue rhs){
		return new LongValue(this.coerceToLong().multiply(rhs.coerceToLong()));
	}
	public NumericValue divide(NumericValue rhs){
		return new LongValue(this.coerceToLong().divide(rhs.coerceToLong()));
	}
	public abstract BigInteger coerceToLong();
	public abstract boolean coercibleToInt();
	public abstract long coerceToInt();
}
