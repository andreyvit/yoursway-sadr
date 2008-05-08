package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IntegerLiteralC;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

//TODO generalize implementation for built-in types.
public class IntType extends PythonClassType implements PythonClass {
    
    private IntType() {
        FunctionObject addFunc = new FunctionObject() {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args) {
                RuntimeObject runtimeObject = args.get(0);
                return runtimeObject;
            }
        };
        setAttribute("__add__", addFunc);
    }
    
    private static IntType instance = new IntType();
    
    static IntType instance() {
        return instance;
    }
    
    public static RuntimeObject newIntObject(IntegerLiteralC literal) {
        IntegerValue integerValue = new IntegerValue(literal.node().getIntValue());
        return new PythonObjectWithValue(instance(), integerValue, literal);
    }
    
    @Override
    public String describe() {
        return "int";
    }
}
