package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.integer_literals.BooleanValue;
import com.yoursway.sadr.blocks.integer_literals.NumericValue;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python_v2.constructs.BooleanLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public class BooleanType extends NumericType {
    @Override
    public RuntimeObject __nonzero__(PythonArguments args) {
        RuntimeObject val = args.readSingle();
        if (val instanceof BooleanType)
            return wrap(false);
        else if (val instanceof PythonValue)
            return coerce((PythonValue<?>) val);
        else
            return null;
    }
    
    protected static RuntimeObject coerce(PythonValue<?> var) {
        AbstractValue value = var.getValue();
        if (value instanceof BooleanValue)
            return var;
        if (value instanceof NumericValue) {
            NumericValue numValue = (NumericValue) value;
            if (numValue.coercibleToInt()) {
                return wrap(numValue.coerceToBool());
            }
            return var;
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
    
    private static BooleanType instance = new BooleanType();
    
    public static BooleanType instance() {
        return instance;
    }
    
    public static PythonValue<BooleanValue> wrap(BooleanLiteralC literal) {
        BooleanValue booleanValue = new BooleanValue(literal.getValue());
        return new PythonValue<BooleanValue>(instance(), booleanValue, literal);
    }
    
    public static PythonValue<BooleanValue> wrap(boolean value) {
        BooleanValue booleanValue = new BooleanValue(value);
        return new PythonValue<BooleanValue>(instance(), booleanValue, null);
    }
    
    @Override
    public String describe() {
        return "bool";
    }
}
