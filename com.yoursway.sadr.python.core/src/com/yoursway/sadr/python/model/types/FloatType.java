package com.yoursway.sadr.python.model.types;

import com.yoursway.sadr.python.constructs.FloatLiteralC;
import com.yoursway.sadr.python.model.values.FloatValue;
import com.yoursway.sadr.python.model.values.NumericValue;
import com.yoursway.sadr.python.model.values.StringValue;
import com.yoursway.sadr.python.objects.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class FloatType extends NumericType {
    public PythonValue __call__(RuntimeArguments args) throws PythonException {
        PythonValue val = args.readSingle();
        if (val instanceof FloatType)
            return wrap(0.0);
        else
            return cast(val);
    }
    
    public static PythonValue cast(PythonValue var) throws PythonException {
        if (var instanceof FloatValue)
            return var;
        if (var instanceof NumericValue) {
            return wrap(((NumericValue) var).coerceToFloat());
        }
        if (var instanceof StringValue) {
            StringValue stringValue = (StringValue) var;
            return wrap(stringValue.coerceToFloat());
        }
        throw new CoersionFailed();
    }
    
    private FloatType() {
    }
    
    public static FloatType instance = new FloatType();
    
    public static FloatValue wrap(FloatLiteralC literal) {
        FloatValue integerValue = new FloatValue(literal.node().getDoubleValue());
        return integerValue;
    }
    
    public static FloatValue wrap(double value) {
        return new FloatValue(value);
    }
    
    @Override
    public String describe() {
        return "float";
    }
    
    @Override
    public NumericValue coerce(PythonValue val) throws PythonException {
        return (NumericValue) val;
    }
    
}
