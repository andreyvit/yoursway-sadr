package com.yoursway.sadr.python_v2.model.builtins.types;

import java.math.BigInteger;

import com.yoursway.sadr.python_v2.constructs.BigIntegerLiteralC;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.values.LongValue;
import com.yoursway.sadr.python_v2.model.builtins.values.NumericValue;
import com.yoursway.sadr.python_v2.model.builtins.values.RedirectFunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.values.StringValue;

public class LongType extends NumericType {
    @Override
    public PythonObject __long__(RuntimeArguments args) throws PythonException {
        PythonObject val = args.readSingle();
        if (val instanceof LongType)
            return wrap(BigInteger.ZERO);
        else if (val instanceof PythonValue)
            return cast((PythonValue) val);
        else
            return null;
    }
    
    public static LongValue cast(PythonValue var) {
        if (var instanceof LongValue)
            return (LongValue) var;
        if (var instanceof NumericValue) {
            return wrap(((NumericValue) var).coerceToLong());
        }
        if (var instanceof StringValue) {
            StringValue stringValue = (StringValue) var;
            return wrap(stringValue.coerceToLong());
        }
        return null;
    }
    
    private LongType() {
        setAttribute(new RedirectFunctionObject("__call__", "__long__"));
    }
    
    public static LongType instance = new LongType();
    
    public static LongValue wrap(BigIntegerLiteralC literal) {
        return new LongValue(literal.node().getLongValue());
    }
    
    public static LongValue wrap(BigInteger value) {
        return new LongValue(value);
    }
    
    @Override
    public String describe() {
        return "long";
    }
    
    @Override
    public NumericValue coerce(PythonObject val) throws PythonException {
        if (val instanceof LongValue)
            return (NumericValue) val;
        throw new CoersionFailed();
    }
}
