package com.yoursway.sadr.python_v2.model.builtins.types;

import static com.google.common.collect.Lists.immutableList;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.TypeError;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.values.NumericValue;

public class NumericType extends BuiltinType {
    public NumericType() {
    }
    
    public PythonObject __add__(RuntimeArguments args) throws PythonException {
        List<NumericValue> values = NumericType.coerce(2, args);
        NumericValue result = values.get(0).add(values.get(1));
        return result;
    }
    
    public PythonObject __sub__(RuntimeArguments args) throws PythonException {
        List<NumericValue> values = NumericType.coerce(2, args);
        NumericValue result = values.get(0).subtract(values.get(1));
        return result;
    }
    
    public PythonObject __mul__(RuntimeArguments args) throws PythonException {
        List<NumericValue> values = NumericType.coerce(2, args);
        NumericValue result = values.get(0).multiply(values.get(1));
        return result;
    }
    
    public PythonObject __div__(RuntimeArguments args) throws PythonException {
        List<NumericValue> values = NumericType.coerce(2, args);
        NumericValue result = values.get(0).divide(values.get(1));
        return result;
    }
    
    public PythonObject __mod__(RuntimeArguments args) throws PythonException {
        List<NumericValue> values = NumericType.coerce(2, args);
        NumericValue result = values.get(0).modulus(values.get(1));
        return result;
    }
    
    public PythonObject __and__(RuntimeArguments args) throws PythonException {
        List<NumericValue> values = NumericType.coerce(2, args);
        NumericValue result = values.get(0).and(values.get(1));
        return result;
    }
    
    public PythonObject __or__(RuntimeArguments args) throws PythonException {
        List<NumericValue> values = NumericType.coerce(2, args);
        NumericValue result = values.get(0).or(values.get(1));
        return result;
    }
    
    public PythonObject __xor__(RuntimeArguments args) throws PythonException {
        List<NumericValue> values = NumericType.coerce(2, args);
        NumericValue result = values.get(0).xor(values.get(1));
        return result;
    }
    
    public PythonObject __lt__(RuntimeArguments args) throws PythonException {
        List<NumericValue> values = NumericType.coerce(2, args);
        NumericValue result = values.get(0).lt(values.get(1));
        return result;
    }
    
    public PythonObject __eq__(RuntimeArguments args) throws PythonException {
        List<NumericValue> values = NumericType.coerce(2, args);
        NumericValue result = values.get(0).eq(values.get(1));
        return result;
    }
    
    public PythonObject __neg__(RuntimeArguments args) throws PythonException {
        NumericValue value = coerce(args.readSingle());
        return value.neg();
    }
    
    public PythonObject __str__(RuntimeArguments args) throws TypeError {
        PythonObject single = args.readSingle();
        return StringType.wrap(single.toString());
    }
    
    public PythonObject __nonzero__(RuntimeArguments args) throws PythonException {
        PythonObject val = args.readSingle();
        if (val.getType().getClass() == this.getClass())
            return BooleanType.instance.coerce(val);
        else
            return null;
    }
    
    public PythonObject __long__(RuntimeArguments args) throws PythonException {
        PythonObject val = args.readSingle();
        if (val.getType().getClass() == this.getClass())
            return LongType.instance.coerce(val);
        else
            return null;
    }
    
    public PythonObject __int__(RuntimeArguments args) throws PythonException {
        PythonObject val = args.readSingle();
        if (val.getType().getClass() == this.getClass())
            return IntegerType.instance.coerce(val);
        else
            return null;
    }
    
    public PythonObject __float__(RuntimeArguments args) throws PythonException {
        PythonObject val = args.readSingle();
        if (val.getType() instanceof NumericType)
            return FloatType.instance.coerce(val);
        else
            return null;
    }
    
    public PythonObject __complex__(RuntimeArguments args) throws PythonException {
        PythonObject val = args.readSingle();
        if (val.getType().getClass() == this.getClass())
            return ComplexType.instance.coerce(val);
        else
            return null;
    }
    
    @Override
    public NumericValue coerce(PythonObject val) throws PythonException {
        if (val instanceof NumericValue)
            return (NumericValue) val;
        throw new PythonException();
    }
    
    static List<NumericType> getTypes() {
        return immutableList(ComplexType.instance, FloatType.instance, LongType.instance,
                IntegerType.instance);
    }
    
    public static NumericValue coerce1(PythonObject val) throws PythonException {
        if (val instanceof NumericValue)
            return (NumericValue) val;
        throw new CoersionFailed();
    }
    
    public static List<NumericValue> coerce(int size, RuntimeArguments args) throws PythonException {
        List<PythonObject> values = args.readArgs(size);
        for (NumericType type : getTypes()) {
            if (hasType(values, type)) {
                List<NumericValue> output = new ArrayList<NumericValue>();
                for (PythonObject value : values) {
                    output.add(type.coerce(value));
                }
                return output;
            }
        }
        throw new CoersionFailed();
    }
}
