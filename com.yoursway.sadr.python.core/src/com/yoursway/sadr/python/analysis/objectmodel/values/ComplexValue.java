package com.yoursway.sadr.python.analysis.objectmodel.values;

import java.math.BigInteger;

import com.yoursway.sadr.python.analysis.objectmodel.types.ComplexType;
import com.yoursway.sadr.python.analysis.objectmodel.types.PythonType;

public class ComplexValue extends NumericValue {
    
    private final Complex value;
    
    public ComplexValue(Complex v) {
        this.value = v;
    }
    
    public Complex value() {
        return value;
    }
    
    @Override
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
    
    @Override
    public boolean coercibleToInt() {
        return false;
    }
    
    @Override
    public long coerceToInt() {
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
    
    @Override
    public Complex coerceToComplex() {
        return value();
    }
    
    @Override
    public boolean coerceToBool() {
        return !value.isZero();
    }
    
    @Override
    public PythonType getType() {
        return ComplexType.instance;
    }
    
    @Override
    public NumericValue add(NumericValue rhs) {
        return new ComplexValue(value.add(rhs.coerceToComplex()));
    }
    
    @Override
    public NumericValue subtract(NumericValue rhs) {
        return new ComplexValue(value.subtract(rhs.coerceToComplex()));
    }
    
    @Override
    public NumericValue multiply(NumericValue rhs) {
        return new ComplexValue(value.multiply(rhs.coerceToComplex()));
    }
    
    @Override
    public NumericValue divide(NumericValue rhs) {
        return new ComplexValue(value.divide(rhs.coerceToComplex()));
    }
    
    @Override
    public NumericValue modulus(NumericValue rhs) {
        return this;
    }
    
    @Override
    public NumericValue and(NumericValue rhs) {
        return null;
    }
    
    @Override
    public NumericValue or(NumericValue rhs) {
        return null;
    }
    
    @Override
    public NumericValue xor(NumericValue rhs) {
        return null;
    }
    
    @Override
    public NumericValue neg() {
        return new ComplexValue(value.neg());
    }
    
    @Override
    public NumericValue pos() {
        return new ComplexValue(value);
    }
    
    @Override
    public NumericValue lt(NumericValue rhs) {
        return null;
    }
    
    @Override
    public NumericValue eq(NumericValue rhs) {
        return null;
    }
    
}
