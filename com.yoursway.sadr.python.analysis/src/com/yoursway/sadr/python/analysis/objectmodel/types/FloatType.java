package com.yoursway.sadr.python.analysis.objectmodel.types;

import com.yoursway.sadr.python.analysis.objectmodel.values.FloatValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.NumericValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.StringValue;

public class FloatType extends NumericType {
    //    public PythonValue __call__(RuntimeArguments args) throws PythonException {
    //        PythonValue val = args.readSingle();
    //        if (val instanceof FloatType)
    //            return wrap(0.0);
    //        else
    //            return cast(val);
    //    }
    
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
