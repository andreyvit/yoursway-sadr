package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.values.BooleanValue;
import com.yoursway.sadr.python_v2.constructs.BooleanLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public class BooleanType extends PythonClassType {
    public RuntimeObject __and__(PythonArguments args) {
        List<BooleanValue> values = args.castArgs(2, BooleanValue.class);
        BooleanValue result = values.get(0).and(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __or__(PythonArguments args) {
        List<BooleanValue> values = args.castArgs(2, BooleanValue.class);
        BooleanValue result = values.get(0).or(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __xor__(PythonArguments args) {
        List<BooleanValue> values = args.castArgs(2, BooleanValue.class);
        BooleanValue result = values.get(0).xor(values.get(1));
        return wrap(result);
    }
    
    private BooleanType() {
        setAttribute(new RedirectFunctionObject("__call__", "__nonzero__"));
    }
    
    private static BooleanType instance = new BooleanType();
    
    public static BooleanType instance() {
        return instance;
    }
    
    public static PythonValue<BooleanValue> wrap(BooleanLiteralC literal) {
        BooleanValue integerValue = new BooleanValue(literal.getValue());
        return new PythonValue<BooleanValue>(instance(), integerValue, literal);
    }
    
    public static PythonValue<BooleanValue> wrap(BooleanValue value) {
        return new PythonValue<BooleanValue>(instance(), value);
    }
    
    @Override
    public String describe() {
        return "bool";
    }
}
