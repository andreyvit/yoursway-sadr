package com.yoursway.sadr.python.model.values;

import java.math.BigInteger;

import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public abstract class NumericValue extends PythonValue {
    
    public Complex coerceToComplex() {
        return new Complex(coerceToFloat());
    }
    
    public abstract boolean coercibleToInt();
    
    public abstract long coerceToInt();
    
    public abstract boolean coerceToBool();
    
    public abstract BigInteger coerceToLong();
    
    public abstract double coerceToFloat();
    
    public abstract NumericValue eq(NumericValue rhs);
    
    public abstract NumericValue lt(NumericValue rhs);
    
    public abstract NumericValue pos();
    
    public abstract NumericValue neg();
    
    public abstract NumericValue xor(NumericValue rhs);
    
    public abstract NumericValue or(NumericValue rhs);
    
    public abstract NumericValue and(NumericValue rhs);
    
    public abstract NumericValue modulus(NumericValue rhs);
    
    public abstract NumericValue divide(NumericValue rhs);
    
    public abstract NumericValue multiply(NumericValue rhs);
    
    public abstract NumericValue subtract(NumericValue rhs);
    
    public abstract NumericValue add(NumericValue rhs);
}
