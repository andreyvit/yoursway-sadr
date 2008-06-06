package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.integer_literals.LongValue;
import com.yoursway.sadr.python_v2.constructs.BigIntegerLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public class LongType extends PythonClassType {
    public RuntimeObject __call__(PythonArguments args) {
        LongValue value = args.castSingle(LongValue.class);
        return wrap(value);
    }
    
    private LongType() {
    }
    
    private static LongType instance;
    
    public static LongType instance() {
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
