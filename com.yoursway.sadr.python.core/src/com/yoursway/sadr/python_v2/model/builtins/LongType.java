package com.yoursway.sadr.python_v2.model.builtins;

import java.math.BigInteger;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.integer_literals.LongValue;
import com.yoursway.sadr.blocks.integer_literals.NumericValue;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python_v2.constructs.BigIntegerLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public class LongType extends NumericType {
    @Override
    public RuntimeObject __long__(PythonArguments args) {
        RuntimeObject val = args.readSingle();
        if (val instanceof LongType)
            return wrap(BigInteger.ZERO);
        else if (val instanceof PythonValue)
            return coerce((PythonValue<?>) val);
        else
            return null;
    }
    
    public static RuntimeObject coerce(PythonValue<?> var) {
        AbstractValue value = var.getValue();
        if (value instanceof LongValue)
            return var;
        if (value instanceof NumericValue) {
            return wrap(((NumericValue) value).coerceToLong());
        }
        if (value instanceof StringValue) {
            StringValue stringValue = (StringValue) value;
            return wrap(stringValue.coerceToLong());
        }
        return null;
    }
    
    private LongType() {
        setAttribute(new RedirectFunctionObject("__call__", "__long__"));
    }
    
    private static LongType instance;
    
    public static LongType instance() {
        if (instance == null) {
            instance = new LongType();
        }
        return instance;
    }
    
    public static PythonValue<LongValue> wrap(BigIntegerLiteralC literal) {
        LongValue integerValue = new LongValue(literal.node().getLongValue());
        return new PythonValue<LongValue>(instance(), integerValue, literal);
    }
    
    public static PythonValue<LongValue> wrap(BigInteger value) {
        return new PythonValue<LongValue>(instance(), new LongValue(value));
    }
    
    @Override
    public String describe() {
        return "long";
    }
}
