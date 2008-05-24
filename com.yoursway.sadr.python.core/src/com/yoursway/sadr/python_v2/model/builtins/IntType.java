package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.python_v2.constructs.IntegerLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class IntType extends PythonClassType {
    public RuntimeObject __call__(PythonArguments args) {
        IntegerValue value = args.castSingle(instance());
        return wrap(value);
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
    
    private static IntType instance;
    
    static IntType instance() {
        if (instance == null) {
            instance = new IntType();
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
