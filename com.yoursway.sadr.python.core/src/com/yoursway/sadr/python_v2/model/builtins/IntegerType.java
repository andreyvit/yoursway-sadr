package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.blocks.integer_literals.NumericValue;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python_v2.constructs.IntegerLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public class IntegerType extends NumericType {
    @Override
    public RuntimeObject __int__(PythonArguments args) {
        RuntimeObject val = args.readSingle();
        if (val instanceof IntegerType)
            return wrap(0);
        else if (val instanceof PythonValue)
            return coerce((PythonValue<?>) val);
        else
            return null;
    }
    
    protected static RuntimeObject coerce(PythonValue<?> var) {
        AbstractValue value = var.getValue();
        if (value instanceof IntegerValue)
            return var;
        if (value instanceof NumericValue) {
            NumericValue numValue = (NumericValue) value;
            if (numValue.coercibleToInt()) {
                return wrap(numValue.coerceToInt());
            }
            return var;
        }
        if (value instanceof StringValue) {
            StringValue stringValue = (StringValue) value;
            return wrap(stringValue.coerceToInt());
        }
        return null;
    }
    
    public IntegerType() {
        setAttribute(new RedirectFunctionObject("__call__", "__int__"));
    }
    
    private static NumericType instance;
    
    public static NumericType instance() {
        if (instance == null) {
            instance = new IntegerType();
        }
        return instance;
    }
    
    public static PythonValue<IntegerValue> wrap(IntegerLiteralC literal) {
        IntegerValue integerValue = new IntegerValue(literal.node().getIntValue());
        return new PythonValue<IntegerValue>(instance(), integerValue, literal);
    }
    
    public static RuntimeObject wrap(long value) {
        return wrap(new IntegerValue(value));
    }
    
    @Override
    public String describe() {
        return "int";
    }
    
}
