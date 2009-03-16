package com.yoursway.sadr.python_v2.model.builtins.values;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

import com.yoursway.sadr.python_v2.model.builtins.types.FloatType;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonType;

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
    
    @Override
    public String describe() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setGroupingUsed(false); // don't group by threes
        nf.setMaximumFractionDigits(18);
        nf.setMinimumFractionDigits(1);
        return nf.format(value);
    }
    
    @Override
    public boolean coercibleToInt() {
        return true;
    }
    
    @Override
    public long coerceToInt() {
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
    
    @Override
    public PythonType getType() {
        return FloatType.instance;
    }
    
    @Override
    public FloatValue add(NumericValue rhs) {
        return new FloatValue(value + rhs.coerceToFloat());
    }
    
    @Override
    public FloatValue subtract(NumericValue rhs) {
        return new FloatValue(value - rhs.coerceToFloat());
    }
    
    @Override
    public FloatValue multiply(NumericValue rhs) {
        return new FloatValue(value * rhs.coerceToFloat());
    }
    
    @Override
    public FloatValue divide(NumericValue rhs) {
        return new FloatValue(value / rhs.coerceToFloat());
    }
    
    @Override
    public FloatValue modulus(NumericValue rhs) {
        return new FloatValue(value % rhs.coerceToFloat());
    }
    
    @Override
    public FloatValue and(NumericValue rhs) {
        return null;
    }
    
    @Override
    public FloatValue or(NumericValue rhs) {
        return null;
    }
    
    @Override
    public FloatValue xor(NumericValue rhs) {
        return null;
    }
    
    @Override
    public FloatValue neg() {
        return new FloatValue(-value);
    }
    
    @Override
    public FloatValue pos() {
        return new FloatValue(+value);
    }
    
    @Override
    public BooleanValue lt(NumericValue rhs) {
        return BooleanValue.instance(value < rhs.coerceToFloat());
    }
    
    @Override
    public BooleanValue eq(NumericValue rhs) {
        return BooleanValue.instance(value == rhs.coerceToFloat());
    }
    
}
