package com.yoursway.sadr.python.model.types;

import java.math.BigInteger;

import com.yoursway.sadr.python.constructs.BigIntegerLiteralC;
import com.yoursway.sadr.python.model.values.LongValue;
import com.yoursway.sadr.python.model.values.NumericValue;
import com.yoursway.sadr.python.model.values.StringValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class LongType extends NumericType {
    //    @Override
    //    public PythonValue __long__(RuntimeArguments args) throws PythonException {
    //        PythonValue val = args.readSingle();
    //        if (val instanceof LongType)
    //            return wrap(BigInteger.ZERO);
    //        else
    //            return cast(val);
    //    }
    
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
        //        setAttribute(new RedirectFunctionObject("__call__", "__long__"));
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
    public NumericValue coerce(PythonValue val) throws PythonException {
        if (val instanceof LongValue)
            return (NumericValue) val;
        throw new CoersionFailed();
    }
}
