package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.integer_literals.BooleanValue;
import com.yoursway.sadr.blocks.integer_literals.ComplexValue;
import com.yoursway.sadr.blocks.integer_literals.FloatValue;
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
    
    public RuntimeObject __mod__(PythonArguments args) {
        List<NumericValue> values = args.castArgs(2, NumericValue.class);
        NumericValue result = values.get(0).modulus(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __and__(PythonArguments args) {
        List<NumericValue> values = args.castArgs(2, NumericValue.class);
        NumericValue result = values.get(0).and(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __or__(PythonArguments args) {
        List<NumericValue> values = args.castArgs(2, NumericValue.class);
        NumericValue result = values.get(0).or(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __xor__(PythonArguments args) {
        List<NumericValue> values = args.castArgs(2, NumericValue.class);
        NumericValue result = values.get(0).xor(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __lt__(PythonArguments args) {
        List<NumericValue> values = args.castArgs(2, NumericValue.class);
        NumericValue result = values.get(0).lt(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __eq__(PythonArguments args) {
        List<NumericValue> values = args.castArgs(2, NumericValue.class);
        NumericValue result = values.get(0).eq(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __neg__(PythonArguments args) {
        NumericValue value = args.castSingle(NumericValue.class);
        return wrap(value.neg());
    }
    
    public RuntimeObject __str__(PythonArguments args) {
        RuntimeObject single = args.readSingle();
        return StringType.wrap(single.toString());
    }
    
    public static PythonValue<NumericValue> wrap(NumericValue value) {
        if (value instanceof BooleanValue)
            return new PythonValue<NumericValue>(BooleanType.instance(), value);
        if (value instanceof IntegerValue)
            return new PythonValue<NumericValue>(IntegerType.instance(), value);
        else if (value instanceof LongValue)
            return new PythonValue<NumericValue>(LongType.instance(), value);
        else if (value instanceof FloatValue)
            return new PythonValue<NumericValue>(FloatType.instance(), value);
        else if (value instanceof ComplexValue)
            return new PythonValue<NumericValue>(ComplexType.instance(), value);
        throw new IllegalArgumentException();
    }
    
    public RuntimeObject __nonzero__(PythonArguments args) {
        RuntimeObject val = args.readSingle();
        if (val.getType().getClass() == this.getClass())
            return BooleanType.coerce((PythonValue<?>) val);
        else
            return null;
    }
    
    public RuntimeObject __long__(PythonArguments args) {
        RuntimeObject val = args.readSingle();
        if (val.getType().getClass() == this.getClass())
            return LongType.coerce((PythonValue<?>) val);
        else
            return null;
    }
    
    public RuntimeObject __int__(PythonArguments args) {
        RuntimeObject val = args.readSingle();
        if (val.getType().getClass() == this.getClass())
            return IntegerType.coerce((PythonValue<?>) val);
        else
            return null;
    }
    
    public RuntimeObject __float__(PythonArguments args) {
        RuntimeObject val = args.readSingle();
        if (val.getType().getClass() == this.getClass())
            return FloatType.coerce((PythonValue<?>) val);
        else
            return null;
    }
    
    public RuntimeObject __complex__(PythonArguments args) {
        RuntimeObject val = args.readSingle();
        if (val.getType().getClass() == this.getClass())
            return ComplexType.coerce((PythonValue<?>) val);
        else
            return null;
    }
}
