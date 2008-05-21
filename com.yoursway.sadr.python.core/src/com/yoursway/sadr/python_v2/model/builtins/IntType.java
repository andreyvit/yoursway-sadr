package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IntegerLiteralC;
import com.yoursway.sadr.python_v2.model.ArgumentsUtil;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

//TODO generalize implementation for built-in types.
public class IntType extends PythonClassType {
    private static List<IntegerValue> castArgs(PythonArguments args) {
        return ArgumentsUtil.getCastedArgs(args, 2, instance());
    }
    
    private IntType() {
        setAttribute(new FunctionObject("__add__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                List<IntegerValue> castedArgs = castArgs(args);
                IntegerValue result = castedArgs.get(0).add(castedArgs.get(1));
                return wrap(result);
            }
        });
        
        setAttribute(new FunctionObject("__sub__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                List<IntegerValue> castedArgs = castArgs(args);
                IntegerValue result = castedArgs.get(0).subtract(castedArgs.get(1));
                return wrap(result);
            }
        });
        
        setAttribute(new FunctionObject("__mul__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                List<IntegerValue> castedArgs = castArgs(args);
                IntegerValue result = castedArgs.get(0).multiply(castedArgs.get(1));
                return wrap(result);
            }
        });
        
        setAttribute(new FunctionObject("__div__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                List<IntegerValue> castedArgs = castArgs(args);
                IntegerValue result = castedArgs.get(0).divide(castedArgs.get(1));
                return wrap(result);
            }
        });
    }
    
    private static IntType instance;
    
    static IntType instance() {
        if (instance == null) {
            instance = new IntType();
        }
        return instance;
    }
    
    public static PythonObjectWithValue<IntegerValue> wrap(IntegerLiteralC literal) {
        IntegerValue integerValue = new IntegerValue(literal.node().getIntValue());
        return new PythonObjectWithValue<IntegerValue>(instance(), integerValue, literal);
    }
    
    public static PythonObjectWithValue<IntegerValue> wrap(IntegerValue value) {
        return new PythonObjectWithValue<IntegerValue>(instance(), value);
    }
    
    @Override
    public String describe() {
        return "int";
    }
}
