package com.yoursway.sadr.python.analysis.objectmodel.types;

import com.yoursway.sadr.python.analysis.objectmodel.values.Complex;
import com.yoursway.sadr.python.analysis.objectmodel.values.ComplexValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.NumericValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.StringValue;

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
    
    public static ComplexValue wrap(double re, double im) {
        Complex value = new Complex(re, im);
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
