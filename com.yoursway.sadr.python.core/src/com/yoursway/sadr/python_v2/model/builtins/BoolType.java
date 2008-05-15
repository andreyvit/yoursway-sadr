package com.yoursway.sadr.python_v2.model.builtins;

import java.util.HashMap;
import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.BooleanLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.values.BooleanValue;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

//TODO generalize implementation for built-in types.
public class BoolType extends PythonClassType {
    private static PythonObjectWithValue<BooleanValue>[] castArguments(List<RuntimeObject> args) {
        assert args.size() == 2;
        return new PythonObjectWithValue[] { (PythonObjectWithValue<BooleanValue>) args.get(0),
                (PythonObjectWithValue<BooleanValue>) args.get(1) };
    }
    
    private BoolType() {
        FunctionObject and = new FunctionObject("__and__") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                PythonObjectWithValue<BooleanValue>[] castedArgs = castArguments(args);
                BooleanValue result = castedArgs[0].getValue().and(castedArgs[1].getValue());
                return newBooleanObject(result);
            }
        };
        
        FunctionObject or = new FunctionObject("__or__") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                PythonObjectWithValue<BooleanValue>[] castedArgs = castArguments(args);
                BooleanValue result = castedArgs[0].getValue().or(castedArgs[1].getValue());
                return newBooleanObject(result);
            }
        };
        
        FunctionObject xor = new FunctionObject("__xor__") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                PythonObjectWithValue<BooleanValue>[] castedArgs = castArguments(args);
                BooleanValue result = castedArgs[0].getValue().xor(castedArgs[1].getValue());
                return newBooleanObject(result);
            }
        };
        
        FunctionObject[] functions = new FunctionObject[] { and, or, xor };
        
        for (FunctionObject func : functions) {
            setAttribute(func.name(), func);
        }
    }
    
    private static BoolType instance = new BoolType();
    
    static BoolType instance() {
        return instance;
    }
    
    public static PythonObjectWithValue<BooleanValue> newBooleanObject(BooleanLiteralC literal) {
        BooleanValue integerValue = new BooleanValue(literal.getValue());
        return new PythonObjectWithValue<BooleanValue>(instance(), integerValue, literal);
    }
    
    public static PythonObjectWithValue<BooleanValue> newBooleanObject(BooleanValue value) {
        return new PythonObjectWithValue<BooleanValue>(instance(), value);
    }
    
    @Override
    public String describe() {
        return "bool";
    }
}
