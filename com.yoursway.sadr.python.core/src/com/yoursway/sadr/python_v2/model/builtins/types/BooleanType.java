package com.yoursway.sadr.python_v2.model.builtins.types;

import com.yoursway.sadr.python_v2.constructs.BooleanLiteralC;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.values.BooleanValue;
import com.yoursway.sadr.python_v2.model.builtins.values.NumericValue;
import com.yoursway.sadr.python_v2.model.builtins.values.RedirectFunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.values.StringValue;

public class BooleanType extends NumericType {
    @Override
    public PythonObject __nonzero__(RuntimeArguments args) throws PythonException {
        PythonObject val = args.readSingle();
        if (val instanceof BooleanType)
            return BooleanValue.instance_false;
        else if (val instanceof PythonValue)
            return coerce(val);
        else
            return null;
    }
    
    @Override
    public BooleanValue coerce(PythonObject value) throws PythonException {
        if (value instanceof BooleanValue)
            return (BooleanValue) value;
        if (value instanceof NumericValue) {
            NumericValue numValue = (NumericValue) value;
            if (numValue.coercibleToInt()) {
                return wrap(numValue.coerceToBool());
            }
            throw new CoersionFailed();
        }
        if (value instanceof StringValue) {
            StringValue stringValue = (StringValue) value;
            return wrap(stringValue.coerceToBool());
        }
        return null;
    }
    
    private BooleanType() {
        setAttribute(new RedirectFunctionObject("__call__", "__nonzero__"));
    }
    
    public static BooleanType instance = new BooleanType();
    
    public static BooleanValue wrap(BooleanLiteralC literal) {
        return BooleanValue.instance(literal.getValue());
    }
    
    public static BooleanValue wrap(boolean value) {
        return (value) ? BooleanValue.instance_true : BooleanValue.instance_false;
    }
    
    @Override
    public String describe() {
        return "bool";
    }
}
