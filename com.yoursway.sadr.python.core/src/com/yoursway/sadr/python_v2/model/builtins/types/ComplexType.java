package com.yoursway.sadr.python_v2.model.builtins.types;

import com.yoursway.sadr.python_v2.constructs.ComplexLiteralC;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.values.Complex;
import com.yoursway.sadr.python_v2.model.builtins.values.ComplexValue;
import com.yoursway.sadr.python_v2.model.builtins.values.NumericValue;
import com.yoursway.sadr.python_v2.model.builtins.values.RedirectFunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.values.StringValue;

public class ComplexType extends NumericType {
    @Override
    public PythonObject __long__(RuntimeArguments args) throws PythonException {
        PythonObject val = args.readSingle();
        if (val instanceof ComplexType)
            return wrap(new Complex(0));
        else if (val instanceof PythonValue)
            return coerce(val);
        else
            return null;
    }
    
    @Override
    public ComplexValue coerce(PythonObject var) throws PythonException {
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
        setAttribute(new RedirectFunctionObject("__call__", "__complex__"));
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
