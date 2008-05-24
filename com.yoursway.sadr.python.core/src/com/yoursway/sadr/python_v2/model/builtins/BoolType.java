package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.values.BooleanValue;
import com.yoursway.sadr.python_v2.constructs.BooleanLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class BoolType extends PythonClassType {
    public RuntimeObject __and__(PythonArguments args) {
        List<BooleanValue> values = args.castArgs(2, instance());
        BooleanValue result = values.get(0).and(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __or__(PythonArguments args) {
        List<BooleanValue> values = args.castArgs(2, instance());
        BooleanValue result = values.get(0).or(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __xor__(PythonArguments args) {
        List<BooleanValue> values = args.castArgs(2, instance());
        BooleanValue result = values.get(0).xor(values.get(1));
        return wrap(result);
    }
    
    private BoolType() {
        setAttribute(new RedirectFunctionObject("__call__", "__nonzero__"));
    }
    
    private static BoolType instance = new BoolType();
    
    static BoolType instance() {
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
