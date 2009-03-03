package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.integer_literals.Complex;
import com.yoursway.sadr.blocks.integer_literals.ComplexValue;
import com.yoursway.sadr.blocks.integer_literals.NumericValue;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python_v2.constructs.ComplexLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public class ComplexType extends NumericType {
    @Override
    public RuntimeObject __long__(PythonArguments args) {
        RuntimeObject val = args.readSingle();
        if (val instanceof ComplexType)
            return wrap(new Complex(0));
        else if (val instanceof PythonValue<?>)
            return coerce((PythonValue<?>) val);
        else
            return null;
    }
    
    public static RuntimeObject coerce(PythonValue<?> var) {
        AbstractValue value = var.getValue();
        if (value instanceof ComplexValue)
            return var;
        if (value instanceof NumericValue) {
            return wrap(((NumericValue) value).coerceToComplex());
        }
        if (value instanceof StringValue) {
            StringValue stringValue = (StringValue) value;
            return wrap(stringValue.coerceToComplex());
        }
        return null;
    }
    
    private ComplexType() {
        setAttribute(new RedirectFunctionObject("__call__", "__complex__"));
    }
    
    private static ComplexType instance;
    
    public static ComplexType instance() {
        if (instance == null) {
            instance = new ComplexType();
        }
        return instance;
    }
    
    public static PythonValue<ComplexValue> wrap(ComplexLiteralC literal) {
        Complex value = new Complex(0, literal.node().getDoubleValue());
        ComplexValue complexValue = new ComplexValue(value);
        return new PythonValue<ComplexValue>(instance(), complexValue, literal);
    }
    
    public static PythonValue<ComplexValue> wrap(Complex value) {
        return new PythonValue<ComplexValue>(instance(), new ComplexValue(value));
    }
    
    @Override
    public String describe() {
        return "complex";
    }
}
