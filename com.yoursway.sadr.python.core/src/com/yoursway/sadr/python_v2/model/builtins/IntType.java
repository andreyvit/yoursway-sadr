package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IntegerLiteralC;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

//TODO generalize implementation for built-in types.
public class IntType extends PythonClassType implements PythonClass {
    private static PythonObjectWithValue<IntegerValue>[] castArguments(List<RuntimeObject> args) {
        assert args.size() == 2;
        return new PythonObjectWithValue[] { 
                (PythonObjectWithValue<IntegerValue>) args.get(0),
                (PythonObjectWithValue<IntegerValue>) args.get(1) };
    }
    
    private IntType() {
        FunctionObject addFunc = new FunctionObject() {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args) {
                PythonObjectWithValue<IntegerValue>[] castedArgs = castArguments(args);
                IntegerValue result = castedArgs[0].getValue().add(castedArgs[1].getValue());
                return new PythonObjectWithValue<IntegerValue>(instance(), result, null);
            }
        };
        setAttribute("__add__", addFunc);
        
        FunctionObject subtract = new FunctionObject() {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args) {
                PythonObjectWithValue<IntegerValue>[] castedArgs = castArguments(args);
                IntegerValue result = castedArgs[0].getValue().subtract(castedArgs[1].getValue());
                return new PythonObjectWithValue<IntegerValue>(instance(), result, null);
            }
        };
        setAttribute("__sub__", subtract);
        
        FunctionObject multiply = new FunctionObject() {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args) {
                PythonObjectWithValue<IntegerValue>[] castedArgs = castArguments(args);
                IntegerValue result = castedArgs[0].getValue().multiply(castedArgs[1].getValue());
                return new PythonObjectWithValue<IntegerValue>(instance(), result, null);
            }
        };
        setAttribute("__mul__", multiply);
        
        FunctionObject divide = new FunctionObject() {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args) {
                PythonObjectWithValue<IntegerValue>[] castedArgs = castArguments(args);
                IntegerValue result = castedArgs[0].getValue().divide(castedArgs[1].getValue());
                return new PythonObjectWithValue<IntegerValue>(instance(), result, null);
            }
        };
        setAttribute("__div__", divide);
    }
    
    private static IntType instance = new IntType();
    
    static IntType instance() {
        return instance;
    }
    
    public static PythonObjectWithValue<IntegerValue> newIntObject(IntegerLiteralC literal) {
        IntegerValue integerValue = new IntegerValue(literal.node().getIntValue());
        return new PythonObjectWithValue<IntegerValue>(instance(), integerValue, literal);
    }
    
    @Override
    public String describe() {
        return "int";
    }
}
