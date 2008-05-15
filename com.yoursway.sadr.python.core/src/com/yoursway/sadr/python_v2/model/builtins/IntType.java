package com.yoursway.sadr.python_v2.model.builtins;

import java.util.HashMap;
import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IntegerLiteralC;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

//TODO generalize implementation for built-in types.
public class IntType extends PythonClassType {
    private static PythonObjectWithValue<IntegerValue>[] castArguments(List<RuntimeObject> args) {
        if (!(args.size() == 2))
            throw new IllegalArgumentException("castArguments require 2 arguments, was " + args.size());
        return new PythonObjectWithValue[] { (PythonObjectWithValue<IntegerValue>) args.get(0),
                (PythonObjectWithValue<IntegerValue>) args.get(1) };
    }
    
    private IntType() {
        FunctionObject addFunc = new FunctionObject("__add__") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                PythonObjectWithValue<IntegerValue>[] castedArgs = castArguments(args);
                IntegerValue result = castedArgs[0].getValue().add(castedArgs[1].getValue());
                return newIntObject(result);
            }
        };
        
        FunctionObject subtract = new FunctionObject("__sub__") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                PythonObjectWithValue<IntegerValue>[] castedArgs = castArguments(args);
                IntegerValue result = castedArgs[0].getValue().subtract(castedArgs[1].getValue());
                return newIntObject(result);
            }
        };
        
        FunctionObject multiply = new FunctionObject("__mul__") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                PythonObjectWithValue<IntegerValue>[] castedArgs = castArguments(args);
                IntegerValue result = castedArgs[0].getValue().multiply(castedArgs[1].getValue());
                return newIntObject(result);
            }
        };
        
        FunctionObject divide = new FunctionObject("__div__") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                PythonObjectWithValue<IntegerValue>[] castedArgs = castArguments(args);
                IntegerValue result = castedArgs[0].getValue().divide(castedArgs[1].getValue());
                return newIntObject(result);
            }
        };
        FunctionObject[] functions = new FunctionObject[] { addFunc, subtract, multiply, divide };
        
        for (FunctionObject func : functions) {
            setAttribute(func.name(), func);
        }
    }
    
    private static IntType instance;
    
    static IntType instance() {
        if (instance == null) {
            instance = new IntType();
        }
        return instance;
    }
    
    public static PythonObjectWithValue<IntegerValue> newIntObject(IntegerLiteralC literal) {
        IntegerValue integerValue = new IntegerValue(literal.node().getIntValue());
        return new PythonObjectWithValue<IntegerValue>(instance(), integerValue, literal);
    }
    
    public static PythonObjectWithValue<IntegerValue> newIntObject(IntegerValue value) {
        return new PythonObjectWithValue<IntegerValue>(instance(), value);
    }
    
    @Override
    public String describe() {
        return "int";
    }
}
