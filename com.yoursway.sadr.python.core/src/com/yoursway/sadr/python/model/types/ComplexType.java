package com.yoursway.sadr.python.model.types;

import com.yoursway.sadr.python.constructs.ComplexLiteralC;
import com.yoursway.sadr.python.model.values.Complex;
import com.yoursway.sadr.python.model.values.ComplexValue;
import com.yoursway.sadr.python.model.values.NumericValue;
import com.yoursway.sadr.python.model.values.StringValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class ComplexType extends NumericType {
    //    @Override
    //    public PythonValue __long__(RuntimeArguments args) throws PythonException {
    //        PythonValue val = args.readSingle();
    //        if (val instanceof ComplexType)
    //            return wrap(new Complex(0));
    //        else
    //            return coerce(val);
    //    }
    
    @Override
    public ComplexValue coerce(PythonValue var) throws PythonException {
        if (var instanceof ComplexValue)
            return (ComplexValue) var;
        if (var instanceof NumericValue) {
            return wrap(((NumericValue) var).coerceToComplex());
        }
        if (var instanceof StringValue) {
            StringValue stringValue = (StringValue) var;
            return wrap(stringValue.coerceToComplex());
        }
        throw new CoersionFailed();
    }
    
    private ComplexType() {
        //        setAttribute(new RedirectFunctionObject("__call__", "__complex__"));
    }
    
    public static ComplexType instance = new ComplexType();
    
    public static ComplexValue wrap(ComplexLiteralC literal) {
        Complex value = new Complex(0, literal.node().getDoubleValue());
        return new ComplexValue(value);
    }
    
    public static ComplexValue wrap(Complex value) {
        return new ComplexValue(value);
    }
    
    @Override
    public String describe() {
        return "complex";
    }
}
