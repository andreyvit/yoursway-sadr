package com.yoursway.sadr.blocks.integer_literals;

import java.math.BigInteger;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;

public abstract class NumericValue extends AbstractValue{
	public NumericValue add(NumericValue rhs){
		if(this instanceof ComplexValue || rhs instanceof ComplexValue)
			return new ComplexValue(this.coerceToComplex().add(rhs.coerceToComplex()));
		if(this instanceof FloatValue || rhs instanceof FloatValue)
			return new FloatValue(this.coerceToFloat() + rhs.coerceToFloat());
		if(this instanceof LongValue || rhs instanceof LongValue)
			return new LongValue(this.coerceToLong().add(rhs.coerceToLong()));
		return new IntegerValue(this.coerceToInt() + rhs.coerceToInt());
	}
	
	public NumericValue subtract(NumericValue rhs){
		if(this instanceof ComplexValue || rhs instanceof ComplexValue)
			return new ComplexValue(this.coerceToComplex().subtract(rhs.coerceToComplex()));
		if(this instanceof FloatValue || rhs instanceof FloatValue)
			return new FloatValue(this.coerceToFloat() - rhs.coerceToFloat());
		if(this instanceof LongValue || rhs instanceof LongValue)
			return new LongValue(this.coerceToLong().subtract(rhs.coerceToLong()));
		return new IntegerValue(this.coerceToInt() - rhs.coerceToInt());
	}
	
	public NumericValue multiply(NumericValue rhs){
		if(this instanceof ComplexValue || rhs instanceof ComplexValue)
			return new ComplexValue(this.coerceToComplex().multiply(rhs.coerceToComplex()));
		if(this instanceof FloatValue || rhs instanceof FloatValue)
			return new FloatValue(this.coerceToFloat() * rhs.coerceToFloat());
		if(this instanceof LongValue || rhs instanceof LongValue)
			return new LongValue(this.coerceToLong().multiply(rhs.coerceToLong()));
		return new IntegerValue(this.coerceToInt() * rhs.coerceToInt());
	}
	
	public NumericValue divide(NumericValue rhs){
		if(this instanceof ComplexValue || rhs instanceof ComplexValue)
			return new ComplexValue(this.coerceToComplex().divide(rhs.coerceToComplex()));
		if(this instanceof FloatValue || rhs instanceof FloatValue)
			return new FloatValue(this.coerceToFloat() / rhs.coerceToFloat());
		if(this instanceof LongValue || rhs instanceof LongValue)
			return new LongValue(this.coerceToLong().divide(rhs.coerceToLong()));
		return new IntegerValue(this.coerceToInt() / rhs.coerceToInt());
	}

	public NumericValue modulus(NumericValue rhs) {
		if(this instanceof ComplexValue || rhs instanceof ComplexValue)
			return this;
		if(this instanceof FloatValue || rhs instanceof FloatValue)
			return new FloatValue(this.coerceToFloat() % rhs.coerceToFloat());
		if(this instanceof LongValue || rhs instanceof LongValue)
			return new LongValue(this.coerceToLong().mod(rhs.coerceToLong()));
		return new IntegerValue(this.coerceToInt() % rhs.coerceToInt());
	}
	
	public NumericValue and(NumericValue rhs) {
		if(this instanceof ComplexValue || rhs instanceof ComplexValue)
			return null;
		if(this instanceof FloatValue || rhs instanceof FloatValue)
			return null;
		if(this instanceof LongValue || rhs instanceof LongValue)
			return new LongValue(this.coerceToLong().and(rhs.coerceToLong()));
		if(this instanceof IntegerValue || rhs instanceof IntegerValue)
			return new IntegerValue(this.coerceToInt() & rhs.coerceToInt());
		return new BooleanValue(this.coerceToBool() && rhs.coerceToBool());
	}
	public NumericValue or(NumericValue rhs) {
		if(this instanceof ComplexValue || rhs instanceof ComplexValue)
			return null;
		if(this instanceof FloatValue || rhs instanceof FloatValue)
			return null;
		if(this instanceof LongValue || rhs instanceof LongValue)
			return new LongValue(this.coerceToLong().or(rhs.coerceToLong()));
		if(this instanceof IntegerValue || rhs instanceof IntegerValue)
			return new IntegerValue(this.coerceToInt() | rhs.coerceToInt());
		return new BooleanValue(this.coerceToBool() || rhs.coerceToBool());
	}
	public NumericValue xor(NumericValue rhs) {
		if(this instanceof ComplexValue || rhs instanceof ComplexValue)
			return null;
		if(this instanceof FloatValue || rhs instanceof FloatValue)
			return null;
		if(this instanceof LongValue || rhs instanceof LongValue)
			return new LongValue(this.coerceToLong().xor(rhs.coerceToLong()));
		if(this instanceof IntegerValue || rhs instanceof IntegerValue)
			return new IntegerValue(this.coerceToInt() ^ rhs.coerceToInt());
		return new BooleanValue(this.coerceToBool() ^ rhs.coerceToBool());
	}
	public NumericValue neg() {
		if(this instanceof ComplexValue)
			return new ComplexValue(this.coerceToComplex().neg());
		if(this instanceof FloatValue)
			return new FloatValue(-this.coerceToFloat());
		if(this instanceof LongValue)
			return new LongValue(this.coerceToLong().negate());
		return new IntegerValue(-this.coerceToInt());
	}
	public NumericValue pos() {
		if(this instanceof ComplexValue)
			return new ComplexValue(this.coerceToComplex());
		if(this instanceof FloatValue)
			return new FloatValue(+this.coerceToFloat());
		if(this instanceof LongValue)
			return new LongValue(this.coerceToLong());
		return new IntegerValue(+this.coerceToInt());
	}
	public abstract boolean coerceToBool();
	public abstract boolean coercibleToInt();
	public abstract long coerceToInt();
	public abstract BigInteger coerceToLong();
	public abstract double coerceToFloat();
	public Complex coerceToComplex() {
		return new Complex(coerceToFloat());
	}
}
