package com.yoursway.sadr.python_v2.model.builtins.types;

import com.yoursway.sadr.python_v2.constructs.IntegerLiteralC;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.values.IntegerValue;
import com.yoursway.sadr.python_v2.model.builtins.values.NumericValue;
import com.yoursway.sadr.python_v2.model.builtins.values.StringValue;

public class IntegerType extends NumericType {
    public IntegerType() {
    }
    
    public PythonObject __call__(RuntimeArguments args) throws PythonException {
        PythonObject val = args.readSingle();
        if (val instanceof IntegerType)
            return wrap(0);
        else if (val instanceof PythonValue)
            return cast(val);
        else
            return null;
    }
    
    public IntegerValue cast(PythonObject val) throws PythonException {
        if (val instanceof IntegerValue)
            return (IntegerValue) val;
        if (val instanceof NumericValue) {
            NumericValue numValue = (NumericValue) val;
            if (numValue.coercibleToInt()) {
                return wrap(numValue.coerceToInt());
            }
            throw new CoersionFailed();
        }
        if (val instanceof StringValue) {
            StringValue stringValue = (StringValue) val;
            return wrap(stringValue.coerceToInt());
        }
        throw new CoersionFailed();
    }
    
    public static IntegerType instance = new IntegerType();
    
    public static NumericValue wrap(IntegerLiteralC literal) {
        return new IntegerValue(literal.node().getIntValue());
    }
    
    public static IntegerValue wrap(long value) {
        return new IntegerValue(value);
    }
    
    @Override
    public String describe() {
        return "int";
    }
    
    @Override
    public IntegerValue coerce(PythonObject val) throws PythonException {
        if (val instanceof IntegerValue)
            return (IntegerValue) val;
        throw new CoersionFailed();
        
    }
}
