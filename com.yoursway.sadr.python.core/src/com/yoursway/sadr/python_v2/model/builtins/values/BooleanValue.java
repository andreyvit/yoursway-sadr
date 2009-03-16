package com.yoursway.sadr.python_v2.model.builtins.values;

import java.math.BigInteger;

import com.yoursway.sadr.python_v2.model.builtins.types.BooleanType;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonType;

public class BooleanValue extends IntegerValue {
    
    private final boolean value;
    public static final String TRUE = "True";
    public static final String FALSE = "False";
    
    public static final BooleanValue instance_true = new BooleanValue(true);
    public static final BooleanValue instance_false = new BooleanValue(false);
    
    private BooleanValue(boolean value) {
        super(value ? 1 : 0);
        this.value = value;
    }
    
    @Override
    public int hashCode() {
        return new Boolean(value).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BooleanValue))
            return false;
        return ((BooleanValue) obj).value == this.value;
    }
    
    @Override
    public String describe() {
        return value ? TRUE : FALSE;
    }
    
    public BooleanValue not() {
        return new BooleanValue(!this.value);
    }
    
    @Override
    public BigInteger coerceToLong() {
        return value ? BigInteger.ONE : BigInteger.ZERO;
    }
    
    @Override
    public boolean coerceToBool() {
        return value;
    }
    
    @Override
    public PythonType getType() {
        return BooleanType.instance;
    }
    
    public NumericValue land(NumericValue rhs) {
        if (this.coerceToBool())
            return rhs;
        else
            return this;
    }
    
    public NumericValue lor(NumericValue rhs) {
        if (this.coerceToBool())
            return this;
        else
            return rhs;
    }
    
    @Override
    public NumericValue and(NumericValue rhs) {
        return instance(this.coerceToBool() & rhs.coerceToBool());
    }
    
    @Override
    public NumericValue or(NumericValue rhs) {
        return instance(this.coerceToBool() | rhs.coerceToBool());
    }
    
    @Override
    public NumericValue xor(NumericValue rhs) {
        return instance(this.coerceToBool() ^ rhs.coerceToBool());
    }
    
    public static BooleanValue instance(boolean b) {
        return b ? instance_true : instance_false;
    }
}
