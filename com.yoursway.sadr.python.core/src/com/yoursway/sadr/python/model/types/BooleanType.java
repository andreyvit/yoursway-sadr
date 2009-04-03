package com.yoursway.sadr.python.model.types;

import com.yoursway.sadr.python.model.values.BooleanValue;
import com.yoursway.sadr.python.model.values.NumericValue;
import com.yoursway.sadr.python.model.values.StringValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class BooleanType extends NumericType {
    //    @Override
    //    public PythonValue __nonzero__(RuntimeArguments args) throws PythonException {
    //        return cast(args.readSingle());
    //    }
    
    public static BooleanValue cast(PythonValue value) throws PythonException {
        if (value instanceof BooleanValue)
            return (BooleanValue) value;
        if (value instanceof NumericValue) {
            NumericValue numValue = (NumericValue) value;
            return wrap(numValue.coerceToBool());
        }
        if (value instanceof StringValue) {
            StringValue stringValue = (StringValue) value;
            return wrap(stringValue.coerceToBool());
        }
        throw new CoersionFailed();
    }
    
    //    private PythonValueSet bool(PythonDynamicContext crocodile, PythonValue pvalue) {
    //        PythonValueSet methods = CallResolver.findMethod(pvalue, "__nonzero__", crocodile);
    //        if (methods.isEmpty()) {
    //            methods = CallResolver.findMethod(pvalue, "__len__", crocodile);
    //        }
    //        if (methods.isEmpty()) {
    //            return new PythonValueSet((!NoneValue.instance.equals(pvalue)), crocodile);
    //        }
    //        PythonValueSet results = new PythonValueSet();
    //        for (PythonValue method : methods) {
    //            PythonValueSet nonzero = CallResolver.callFunction(method, new RuntimeArguments(method),
    //                    crocodile, getDecl());
    //            for (PythonValue nzvalue : nonzero) {
    //                if (nzvalue instanceof IntegerValue) {
    //                    long value = ((IntegerValue) nzvalue).value();
    //                    if (value >= 0) {
    //                        results.addResult(value != 0, crocodile);
    //                    } else {
    //                        results.addException(new TypeError("__nonzero__ should return >= 0"));
    //                    }
    //                } else {
    //                    results.addException(new TypeError("__nonzero__ should return an int"));
    //                }
    //                
    //            }
    //        }
    //        return results;
    //    }
    
    private BooleanType() {
        //        setAttribute(new BuiltinFunctionObject("__call__") {
        //            @Override
        //            public PythonValueSet call(PythonDynamicContext crocodile, RuntimeArguments args) {
        //                PythonValue val;
        //                try {
        //                    val = args.readSingle();
        //                } catch (TypeError e) {
        //                    return PythonValueSet.EMPTY;
        //                }
        //                if (val instanceof NumericValue) {
        //                    return new PythonValueSet(((NumericValue) val).coerceToBool(), crocodile);
        //                } else {
        //                    return bool(crocodile, val);
        //                }
        //            }
        //        });
    }
    
    public static BooleanType instance = new BooleanType();
    
    public static BooleanValue wrap(boolean value) {
        return (value) ? BooleanValue.instance_true : BooleanValue.instance_false;
    }
    
    @Override
    public String describe() {
        return "bool";
    }
    
    @Override
    public BooleanValue coerce(PythonValue val) throws PythonException {
        if (val instanceof BooleanValue)
            return (BooleanValue) val;
        throw new PythonException();
    }
}
