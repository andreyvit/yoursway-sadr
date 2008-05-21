package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.LongValue;
import com.yoursway.sadr.python.core.typeinferencing.constructs.BigIntegerLiteralC;
import com.yoursway.sadr.python_v2.model.ArgumentsUtil;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class LongType extends PythonClassType {
    private static List<LongValue> castArgs(PythonArguments args) {
        return ArgumentsUtil.getCastedArgs(args, 2, instance());
    }
    
    private LongType() {
        setAttribute(new FunctionObject("__add__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                List<LongValue> castedArgs = castArgs(args);
                LongValue result = castedArgs.get(0).add(castedArgs.get(1));
                return newLongObject(result);
            }
        });
        setAttribute(new FunctionObject("__sub__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                List<LongValue> castedArgs = castArgs(args);
                LongValue result = castedArgs.get(0).subtract(castedArgs.get(1));
                return newLongObject(result);
            }
        });
        setAttribute(new FunctionObject("__mul__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                List<LongValue> castedArgs = castArgs(args);
                LongValue result = castedArgs.get(0).multiply(castedArgs.get(1));
                return newLongObject(result);
            }
        });
        setAttribute(new FunctionObject("__div__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                List<LongValue> castedArgs = castArgs(args);
                LongValue result = castedArgs.get(0).divide(castedArgs.get(1));
                return newLongObject(result);
            }
        });
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
