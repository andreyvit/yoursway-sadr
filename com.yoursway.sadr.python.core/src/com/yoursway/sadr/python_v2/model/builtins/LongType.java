package com.yoursway.sadr.python_v2.model.builtins;

import java.util.HashMap;
import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.LongValue;
import com.yoursway.sadr.python.core.typeinferencing.constructs.BigIntegerLiteralC;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

//TODO generalize implementation for built-in types.
public class LongType extends PythonClassType {
    private static PythonObjectWithValue<LongValue>[] castArguments(List<RuntimeObject> args) {
        if (!(args.size() == 2))
            throw new IllegalArgumentException("castArguments require 2 arguments, was " + args.size());
        return new PythonObjectWithValue[] { (PythonObjectWithValue<LongValue>) args.get(0),
                (PythonObjectWithValue<LongValue>) args.get(1) };
    }
    
    private LongType() {
        FunctionObject addFunc = new FunctionObject("__add__") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                PythonObjectWithValue<LongValue>[] castedArgs = castArguments(args);
                LongValue result = castedArgs[0].getValue().add(castedArgs[1].getValue());
                return newLongObject(result);
            }
        };
        
        FunctionObject subtract = new FunctionObject("__sub__") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                PythonObjectWithValue<LongValue>[] castedArgs = castArguments(args);
                LongValue result = castedArgs[0].getValue().subtract(castedArgs[1].getValue());
                return newLongObject(result);
            }
        };
        
        FunctionObject multiply = new FunctionObject("__mul__") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                PythonObjectWithValue<LongValue>[] castedArgs = castArguments(args);
                LongValue result = castedArgs[0].getValue().multiply(castedArgs[1].getValue());
                return newLongObject(result);
            }
        };
        
        FunctionObject divide = new FunctionObject("__div__") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                PythonObjectWithValue<LongValue>[] castedArgs = castArguments(args);
                LongValue result = castedArgs[0].getValue().divide(castedArgs[1].getValue());
                return newLongObject(result);
            }
        };
        FunctionObject[] functions = new FunctionObject[] { addFunc, subtract, multiply, divide };
        
        for (FunctionObject func : functions) {
            setAttribute(func.name(), func);
        }
    }
    
    private static LongType instance;
    
    static LongType instance() {
        if (instance == null) {
            instance = new LongType();
        }
        return instance;
    }
    
    public static PythonObjectWithValue<LongValue> newLongObject(BigIntegerLiteralC literal) {
        LongValue integerValue = new LongValue(literal.node().getLongValue());
        return new PythonObjectWithValue<LongValue>(instance(), integerValue, literal);
    }
    
    public static PythonObjectWithValue<LongValue> newLongObject(LongValue value) {
        return new PythonObjectWithValue<LongValue>(instance(), value);
    }
    
    @Override
    public String describe() {
        return "int";
    }
}
