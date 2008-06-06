package com.yoursway.sadr.blocks.integer_literals;

import java.math.BigInteger;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;

public abstract class NumericValue extends AbstractValue{
	
	public NumericValue add(NumericValue rhs){
		return new LongValue(this.coherseToLong().add(rhs.coherseToLong()));
	}
	public NumericValue subtract(NumericValue rhs){
		return new LongValue(this.coherseToLong().subtract(rhs.coherseToLong()));
	}
	public NumericValue multiply(NumericValue rhs){
		return new LongValue(this.coherseToLong().multiply(rhs.coherseToLong()));
	}
	public NumericValue divide(NumericValue rhs){
		return new LongValue(this.coherseToLong().divide(rhs.coherseToLong()));
	}
	public abstract BigInteger coherseToLong();
	public abstract boolean cohersibleToInt();
	public abstract long coherseToInt();
}
