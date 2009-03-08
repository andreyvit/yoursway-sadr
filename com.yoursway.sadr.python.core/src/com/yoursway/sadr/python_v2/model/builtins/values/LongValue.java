package com.yoursway.sadr.python_v2.model.builtins.values;

import java.math.BigInteger;

import com.yoursway.sadr.python_v2.model.builtins.types.LongType;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonType;

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
    
    @Override
    public String describe() {
        return value.toString() + "L";
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
    
    @Override
    public boolean coercibleToInt() {
        return value().bitLength() < 32;
    }
    
    @Override
    public long coerceToInt() {
        return value().longValue();
    }
    
    @Override
    public BigInteger coerceToLong() {
        return value();
    }
    
    @Override
    public double coerceToFloat() {
        return value().floatValue();
    }
    
    @Override
    public boolean coerceToBool() {
        return value().signum() != 0;
    }
    
    @Override
    public PythonType getType() {
        return LongType.instance;
    }
    
    @Override
    public NumericValue add(NumericValue rhs) {
        return new LongValue(value.add(rhs.coerceToLong()));
    }
    
    @Override
    public NumericValue subtract(NumericValue rhs) {
        return new LongValue(value.subtract(rhs.coerceToLong()));
    }
    
    @Override
    public NumericValue multiply(NumericValue rhs) {
        return new LongValue(value.multiply(rhs.coerceToLong()));
    }
    
    @Override
    public NumericValue divide(NumericValue rhs) {
        return new LongValue(value.divide(rhs.coerceToLong()));
    }
    
    @Override
    public NumericValue modulus(NumericValue rhs) {
        return new LongValue(value.mod(rhs.coerceToLong()));
    }
    
    @Override
    public NumericValue and(NumericValue rhs) {
        return new LongValue(value.and(rhs.coerceToLong()));
    }
    
    @Override
    public NumericValue or(NumericValue rhs) {
        return new LongValue(value.or(rhs.coerceToLong()));
    }
    
    @Override
    public NumericValue xor(NumericValue rhs) {
        return new LongValue(value.xor(rhs.coerceToLong()));
    }
    
    @Override
    public NumericValue neg() {
        return new LongValue(value.negate());
    }
    
    @Override
    public NumericValue pos() {
        return new LongValue(value);
    }
    
    @Override
    public NumericValue lt(NumericValue rhs) {
        return BooleanValue.instance(value.compareTo(rhs.coerceToLong()) < 0);
    }
    
    @Override
    public NumericValue eq(NumericValue rhs) {
        return BooleanValue.instance(value.compareTo(rhs.coerceToLong()) == 0);
    }
    
}
