package com.yoursway.sadr.python_v2.model.builtins.types;

import com.yoursway.sadr.python_v2.constructs.FloatLiteralC;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.values.FloatValue;
import com.yoursway.sadr.python_v2.model.builtins.values.NumericValue;
import com.yoursway.sadr.python_v2.model.builtins.values.StringValue;

public class FloatType extends NumericType {
    public PythonObject __call__(RuntimeArguments args) throws PythonException {
        PythonObject val = args.readSingle();
        if (val instanceof FloatType)
            return wrap(0.0);
        else if (val instanceof PythonValue)
            return cast(val);
        else
            return null;
    }
    
    public static PythonObject cast(PythonObject var) throws PythonException {
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
    public NumericValue coerce(PythonObject val) throws PythonException {
        if (val instanceof PythonValue)
            return (NumericValue) val;
        throw new CoersionFailed();
    }
}
