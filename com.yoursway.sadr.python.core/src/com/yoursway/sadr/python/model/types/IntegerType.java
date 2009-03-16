package com.yoursway.sadr.python.model.types;

import com.yoursway.sadr.python.constructs.IntegerLiteralC;
import com.yoursway.sadr.python.model.CallResolver;
import com.yoursway.sadr.python.model.values.IntegerValue;
import com.yoursway.sadr.python.model.values.LongValue;
import com.yoursway.sadr.python.model.values.NumericValue;
import com.yoursway.sadr.python.model.values.StringValue;
import com.yoursway.sadr.python.objects.RuntimeArguments;
import com.yoursway.sadr.python.objects.TypeError;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class IntegerType extends NumericType {
    public IntegerType() {
        //        setAttribute(new BuiltinFunctionObject("__call__") {
        //            @Override
        //            public PythonValueSet call(PythonDynamicContext crocodile, RuntimeArguments args) {
        //                PythonValue val;
        //                try {
        //                    val = args.readSingle();
        //                } catch (TypeError e) {
        //                    return PythonValueSet.EMPTY;
        //                }
        //                if (val instanceof BooleanType) {
        //                    return new PythonValueSet(false, crocodile);
        //                } else if (val instanceof NumericValue) {
        //                    boolean bool = ((NumericValue) val).coerceToBool();
        //                    return new PythonValueSet(bool, crocodile);
        //                } else if (val instanceof StringValue) {
        //                    StringValue stringValue = (StringValue) val;
        //                    return new PythonValueSet(wrap(stringValue.coerceToInt()), crocodile);
        //                } else {
        //                    PythonValue pvalue = val;
        //                    return _int(crocodile, pvalue);
        //                }
        //            }
        //        });
    }
    
    protected PythonValueSet _int(PythonDynamicContext crocodile, PythonValue pvalue) {
        PythonValueSet methods = CallResolver.findMethod(pvalue, "__int__", crocodile);
        PythonValueSet results = new PythonValueSet();
        if (methods.isEmpty()) {
            results.addException(new TypeError(pvalue.getType() + " instance has no attribute '__int__'"));
            return results;
        }
        for (PythonValue method : methods) {
            PythonValueSet to_int = CallResolver.callFunction(method, new RuntimeArguments(method),
                    crocodile, getDecl());
            for (PythonValue intvalue : to_int) {
                if (intvalue instanceof IntegerValue || intvalue instanceof LongValue) {
                    results.addResult(intvalue);
                } else {
                    PythonType type = intvalue.getType();
                    results.addException(new TypeError("__int__ returned non-int (type " + type + ")"));
                }
                
            }
        }
        return results;
    }
    
    public IntegerValue cast(PythonValue val) throws PythonException {
        if (val instanceof IntegerValue)
            return (IntegerValue) val;
        if (val instanceof NumericValue) {
            NumericValue numValue = (NumericValue) val;
            if (numValue.coercibleToInt()) {
                return wrap(numValue.coerceToInt());
            }
            throw new CoersionFailed();
        }
        if (val instanceof StringValue) {
            StringValue stringValue = (StringValue) val;
            return wrap(stringValue.coerceToInt());
        }
        throw new CoersionFailed();
    }
    
    public static IntegerType instance = new IntegerType();
    
    public static NumericValue wrap(IntegerLiteralC literal) {
        return new IntegerValue(literal.node().getIntValue());
    }
    
    public static IntegerValue wrap(long value) {
        return new IntegerValue(value);
    }
    
    @Override
    public String describe() {
        return "int";
    }
    
    @Override
    public IntegerValue coerce(PythonValue val) throws PythonException {
        if (val instanceof IntegerValue)
            return (IntegerValue) val;
        throw new CoersionFailed();
        
    }
}
