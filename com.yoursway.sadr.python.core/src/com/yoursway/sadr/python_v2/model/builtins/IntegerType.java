package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.python_v2.constructs.IntegerLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class IntegerType extends PythonClassType {
    public RuntimeObject __int__(PythonArguments args) {
        RuntimeObject val = args.readSingle();
        if (val instanceof IntegerType)
            return wrap(0);
        return val.convertValue(instance());
    }
    
    public RuntimeObject __str__(PythonArguments args) {
        RuntimeObject single = args.readSingle();
        return StringType.wrap(single.toString());
    }
    
    public RuntimeObject __add__(PythonArguments args) {
        List<IntegerValue> values = args.castArgs(2, instance());
        IntegerValue result = values.get(0).add(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __sub__(PythonArguments args) {
        List<IntegerValue> values = args.castArgs(2, instance());
        IntegerValue result = values.get(0).subtract(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __mul__(PythonArguments args) {
        List<IntegerValue> values = args.castArgs(2, instance());
        IntegerValue result = values.get(0).multiply(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __div__(PythonArguments args) {
        List<IntegerValue> values = args.castArgs(2, instance());
        IntegerValue result = values.get(0).divide(values.get(1));
        return wrap(result);
    }
    
    public IntegerType() {
        setAttribute(new RedirectFunctionObject("__call__", "__int__"));
    }
    
    private static IntegerType instance;
    
    static IntegerType instance() {
        if (instance == null) {
            instance = new IntegerType();
        }
        return instance;
    }
    
    public static PythonValue<IntegerValue> wrap(IntegerLiteralC literal) {
        IntegerValue integerValue = new IntegerValue(literal.node().getIntValue());
        return new PythonValue<IntegerValue>(instance(), integerValue, literal);
    }
    
    public static PythonValue<IntegerValue> wrap(IntegerValue value) {
        return new PythonValue<IntegerValue>(instance(), value);
    }
    
    public static RuntimeObject wrap(int value) {
        return wrap(new IntegerValue(value));
    }
    
    @Override
    public String describe() {
        return "int";
    }
    
}
