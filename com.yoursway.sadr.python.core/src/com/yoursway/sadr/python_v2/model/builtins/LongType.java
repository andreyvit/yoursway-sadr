package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.LongValue;
import com.yoursway.sadr.python_v2.constructs.BigIntegerLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class LongType extends PythonClassType {
    public RuntimeObject __call__(PythonArguments args) {
        LongValue value = args.castSingle(instance());
        return wrap(value);
    }
    
    public RuntimeObject __add__(PythonArguments args) {
        List<LongValue> values = args.castArgs(2, instance());
        LongValue result = values.get(0).add(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __sub__(PythonArguments args) {
        List<LongValue> values = args.castArgs(2, instance());
        LongValue result = values.get(0).subtract(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __mul__(PythonArguments args) {
        List<LongValue> values = args.castArgs(2, instance());
        LongValue result = values.get(0).multiply(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __div__(PythonArguments args) {
        List<LongValue> values = args.castArgs(2, instance());
        LongValue result = values.get(0).divide(values.get(1));
        return wrap(result);
    }
    
    private LongType() {
    }
    
    private static LongType instance;
    
    static LongType instance() {
        if (instance == null) {
            instance = new LongType();
        }
        return instance;
    }
    
    public static PythonValue<LongValue> wrap(BigIntegerLiteralC literal) {
        LongValue integerValue = new LongValue(literal.node().getLongValue());
        return new PythonValue<LongValue>(instance(), integerValue, literal);
    }
    
    public static PythonValue<LongValue> wrap(LongValue value) {
        return new PythonValue<LongValue>(instance(), value);
    }
    
    @Override
    public String describe() {
        return "long";
    }
}
