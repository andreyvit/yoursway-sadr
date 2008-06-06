package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.blocks.integer_literals.LongValue;
import com.yoursway.sadr.blocks.integer_literals.NumericValue;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public abstract class NumericType extends PythonClassType {
    public RuntimeObject __add__(PythonArguments args) {
        List<NumericValue> values = args.castArgs(2, NumericValue.class);
        NumericValue result = values.get(0).add(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __sub__(PythonArguments args) {
        List<NumericValue> values = args.castArgs(2, NumericValue.class);
        NumericValue result = values.get(0).subtract(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __mul__(PythonArguments args) {
        List<NumericValue> values = args.castArgs(2, NumericValue.class);
        NumericValue result = values.get(0).multiply(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __div__(PythonArguments args) {
        List<NumericValue> values = args.castArgs(2, NumericValue.class);
        NumericValue result = values.get(0).divide(values.get(1));
        return wrap(result);
    }
    
    public static PythonValue<NumericValue> wrap(NumericValue value) {
        if (value instanceof IntegerValue)
            return new PythonValue<NumericValue>(IntegerType.instance(), value);
        else if (value instanceof LongValue)
            return new PythonValue<NumericValue>(LongType.instance(), value);
        else
            throw new IllegalArgumentException();
    }
    
}
