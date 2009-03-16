package com.yoursway.sadr.python.model.values;

import java.math.BigInteger;

import com.yoursway.sadr.python.model.types.BooleanType;
import com.yoursway.sadr.python.model.types.IntegerType;
import com.yoursway.sadr.python.model.types.PythonType;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetBuilder;

public class IntegerValue extends NumericValue {
    
    private final long value;
    
    public IntegerValue(long value) {
        this.value = value;
    }
    
    public long value() {
        return value;
    }
    
    @Override
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
        return res <= Integer.MAX_VALUE && res >= Integer.MIN_VALUE;
    }
    
    @Override
    public NumericValue add(NumericValue rhs) {
        return expand(this.value + ((IntegerValue) rhs).value);
    }
    
    @Override
    public NumericValue subtract(NumericValue rhs) {
        return expand(this.value - ((IntegerValue) rhs).value);
    }
    
    @Override
    public NumericValue multiply(NumericValue rhs) {
        return expand(this.value * ((IntegerValue) rhs).value);
    }
    
    @Override
    public NumericValue divide(NumericValue rhs) {
        return expand(this.value / ((IntegerValue) rhs).value);
    }
    
    @Override
    public BigInteger coerceToLong() {
        return BigInteger.valueOf(value);
    }
    
    @Override
    public long coerceToInt() {
        return value;
    }
    
    @Override
    public boolean coercibleToInt() {
        return true;
    }
    
    @Override
    public double coerceToFloat() {
        return value;
    }
    
    @Override
    public boolean coerceToBool() {
        return value != 0;
    }
    
    @Override
    public PythonType getType() {
        return IntegerType.instance;
    }
    
    @Override
    public NumericValue modulus(NumericValue rhs) {
        return new IntegerValue(this.coerceToInt() % rhs.coerceToInt());
    }
    
    @Override
    public NumericValue and(NumericValue rhs) {
        return new IntegerValue(this.coerceToInt() & rhs.coerceToInt());
    }
    
    @Override
    public NumericValue or(NumericValue rhs) {
        return new IntegerValue(this.coerceToInt() | rhs.coerceToInt());
    }
    
    @Override
    public NumericValue xor(NumericValue rhs) {
        return new IntegerValue(this.coerceToInt() ^ rhs.coerceToInt());
    }
    
    @Override
    public NumericValue neg() {
        return new IntegerValue(-this.coerceToInt());
    }
    
    @Override
    public NumericValue pos() {
        return new IntegerValue(+this.coerceToInt());
    }
    
    @Override
    public NumericValue lt(NumericValue rhs) {
        return BooleanType.wrap((this.coerceToInt() < rhs.coerceToInt()));
    }
    
    @Override
    public NumericValue eq(NumericValue rhs) {
        return BooleanType.wrap((this.coerceToInt() == rhs.coerceToInt()));
    }
    
    @Override
    public void call(PythonDynamicContext dc, PythonValueSetBuilder builder) {
        builder.addResult(new IntegerValue(value + 42));
    }
    
}
