package com.yoursway.sadr.python.analysis.objectmodel.types;

import static com.google.common.collect.Lists.immutableList;

import java.util.List;

import com.yoursway.sadr.python.analysis.objectmodel.values.NumericValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;

public class NumericType extends BuiltinType {
    public NumericType() {
    }
    
    //    
    //    public PythonValue __add__(RuntimeArguments args) throws PythonException {
    //        List<NumericValue> values = NumericType.coerce(2, args);
    //        NumericValue result = values.get(0).add(values.get(1));
    //        return result;
    //    }
    //    
    //    public PythonValue __sub__(RuntimeArguments args) throws PythonException {
    //        List<NumericValue> values = NumericType.coerce(2, args);
    //        NumericValue result = values.get(0).subtract(values.get(1));
    //        return result;
    //    }
    //    
    //    public PythonValue __mul__(RuntimeArguments args) throws PythonException {
    //        List<NumericValue> values = NumericType.coerce(2, args);
    //        NumericValue result = values.get(0).multiply(values.get(1));
    //        return result;
    //    }
    //    
    //    public PythonValue __div__(RuntimeArguments args) throws PythonException {
    //        List<NumericValue> values = NumericType.coerce(2, args);
    //        NumericValue result = values.get(0).divide(values.get(1));
    //        return result;
    //    }
    //    
    //    public PythonValue __mod__(RuntimeArguments args) throws PythonException {
    //        List<NumericValue> values = NumericType.coerce(2, args);
    //        NumericValue result = values.get(0).modulus(values.get(1));
    //        return result;
    //    }
    //    
    //    public PythonValue __and__(RuntimeArguments args) throws PythonException {
    //        List<NumericValue> values = NumericType.coerce(2, args);
    //        NumericValue result = values.get(0).and(values.get(1));
    //        return result;
    //    }
    //    
    //    public PythonValue __or__(RuntimeArguments args) throws PythonException {
    //        List<NumericValue> values = NumericType.coerce(2, args);
    //        NumericValue result = values.get(0).or(values.get(1));
    //        return result;
    //    }
    //    
    //    public PythonValue __xor__(RuntimeArguments args) throws PythonException {
    //        List<NumericValue> values = NumericType.coerce(2, args);
    //        NumericValue result = values.get(0).xor(values.get(1));
    //        return result;
    //    }
    //    
    //    public PythonValue __lt__(RuntimeArguments args) throws PythonException {
    //        List<NumericValue> values = NumericType.coerce(2, args);
    //        NumericValue result = values.get(0).lt(values.get(1));
    //        return result;
    //    }
    //    
    //    public PythonValue __eq__(RuntimeArguments args) throws PythonException {
    //        List<NumericValue> values = NumericType.coerce(2, args);
    //        NumericValue result = values.get(0).eq(values.get(1));
    //        return result;
    //    }
    //    
    //    public PythonValue __neg__(RuntimeArguments args) throws PythonException {
    //        NumericValue value = coerce(args.readSingle());
    //        return value.neg();
    //    }
    //    
    //    public PythonValue __str__(RuntimeArguments args) throws TypeError {
    //        PythonValue single = args.readSingle();
    //        return StringType.wrap(single.toString());
    //    }
    //    
    //    public PythonValue __nonzero__(RuntimeArguments args) throws PythonException {
    //        PythonValue val = args.readSingle();
    //        if (val.getType().getClass() == this.getClass())
    //            return BooleanType.cast(val);
    //        else
    //            return null;
    //    }
    //    
    //    public PythonValue __long__(RuntimeArguments args) throws PythonException {
    //        PythonValue val = args.readSingle();
    //        if (val.getType().getClass() == this.getClass())
    //            return LongType.instance.coerce(val);
    //        else
    //            return null;
    //    }
    //    
    //    public PythonValue __int__(RuntimeArguments args) throws PythonException {
    //        PythonValue val = args.readSingle();
    //        if (val.getType().getClass() == this.getClass())
    //            return IntegerType.instance.coerce(val);
    //        else
    //            return null;
    //    }
    //    
    //    public PythonValue __float__(RuntimeArguments args) throws PythonException {
    //        PythonValue val = args.readSingle();
    //        if (val.getType() instanceof NumericType)
    //            return FloatType.instance.coerce(val);
    //        else
    //            return null;
    //    }
    //    
    //    public PythonValue __complex__(RuntimeArguments args) throws PythonException {
    //        PythonValue val = args.readSingle();
    //        if (val.getType().getClass() == this.getClass())
    //            return ComplexType.instance.coerce(val);
    //        else
    //            return null;
    //    }
    
    @Override
    public NumericValue coerce(PythonValue val) throws PythonException {
        if (val instanceof NumericValue)
            return (NumericValue) val;
        throw new CoersionFailed();
    }
    
    static List<NumericType> getTypes() {
        return immutableList(ComplexType.instance, FloatType.instance, LongType.instance,
            IntegerType.instance);
    }
    
    //    public static List<NumericValue> coerce(int size, RuntimeArguments args) throws PythonException {
    //        List<PythonValue> values = args.readArgs(size);
    //        for (NumericType type : getTypes()) {
    //            if (type.hasType(values)) {
    //                List<NumericValue> output = new ArrayList<NumericValue>();
    //                for (PythonValue value : values) {
    //                    output.add(type.coerce(value));
    //                }
    //                return output;
    //            }
    //        }
    //        throw new CoersionFailed();
    //    }
}
