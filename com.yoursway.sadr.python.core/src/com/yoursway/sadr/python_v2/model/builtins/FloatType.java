package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.integer_literals.FloatValue;
import com.yoursway.sadr.blocks.integer_literals.NumericValue;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python_v2.constructs.FloatLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public class FloatType extends NumericType {
    @Override
    public RuntimeObject __float__(PythonArguments args) {
        RuntimeObject val = args.readSingle();
        if (val instanceof FloatType)
            return wrap(0.0);
        else if (val instanceof PythonValue<?>)
            return coerce((PythonValue<?>) val);
        else
            return null;
    }
    
    public static RuntimeObject coerce(PythonValue<?> var) {
        AbstractValue value = var.getValue();
        if (value instanceof FloatValue)
            return var;
        if (value instanceof NumericValue) {
            return wrap(((NumericValue) value).coerceToFloat());
        }
        if (value instanceof StringValue) {
            StringValue stringValue = (StringValue) value;
            return wrap(stringValue.coerceToFloat());
        }
        return null;
    }
    
    private FloatType() {
        setAttribute(new RedirectFunctionObject("__call__", "__float__"));
    }
    
    private static FloatType instance;
    
    public static FloatType instance() {
        if (instance == null) {
            instance = new FloatType();
        }
        return instance;
    }
    
    public static PythonValue<FloatValue> wrap(FloatLiteralC literal) {
        FloatValue integerValue = new FloatValue(literal.node().getDoubleValue());
        return new PythonValue<FloatValue>(instance(), integerValue, literal);
    }
    
    public static PythonValue<FloatValue> wrap(double value) {
        return new PythonValue<FloatValue>(instance(), new FloatValue(value));
    }
    
    @Override
    public String describe() {
        return "float";
    }
}
