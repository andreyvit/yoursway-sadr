package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.BooleanLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.values.BooleanValue;
import com.yoursway.sadr.python_v2.model.ArgumentsUtil;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

//TODO generalize implementation for built-in types.
public class BoolType extends PythonClassType {
    private static List<BooleanValue> castArguments(PythonArguments args) {
        return ArgumentsUtil.getCastedArgs(args, 2, instance());
    }
    
    private BoolType() {
        FunctionObject and = new FunctionObject("__and__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                List<BooleanValue> casted = castArguments(args);
                BooleanValue result = casted.get(0).and(casted.get(1));
                return newBooleanObject(result);
            }
        };
        
        FunctionObject or = new FunctionObject("__or__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                List<BooleanValue> casted = castArguments(args);
                BooleanValue result = casted.get(0).or(casted.get(1));
                return newBooleanObject(result);
            }
        };
        
        FunctionObject xor = new FunctionObject("__xor__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                List<BooleanValue> casted = castArguments(args);
                BooleanValue result = casted.get(0).xor(casted.get(1));
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
