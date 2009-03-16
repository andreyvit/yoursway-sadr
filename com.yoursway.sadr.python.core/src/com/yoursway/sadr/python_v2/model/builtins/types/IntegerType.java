package com.yoursway.sadr.python_v2.model.builtins.types;

import com.yoursway.sadr.python_v2.constructs.IntegerLiteralC;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.CallResolver;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.TypeError;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.values.BuiltinFunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.values.IntegerValue;
import com.yoursway.sadr.python_v2.model.builtins.values.LongValue;
import com.yoursway.sadr.python_v2.model.builtins.values.NumericValue;
import com.yoursway.sadr.python_v2.model.builtins.values.StringValue;

public class IntegerType extends NumericType {
    public IntegerType() {
        setAttribute(new BuiltinFunctionObject("__call__") {
            @Override
            public PythonValueSet call(Krocodile crocodile, RuntimeArguments args) {
                PythonObject val;
                try {
                    val = args.readSingle();
                } catch (TypeError e) {
                    return PythonValueSet.EMPTY;
                }
                if (val instanceof BooleanType) {
                    return new PythonValueSet(false, crocodile);
                } else if (val instanceof NumericValue) {
                    boolean bool = ((NumericValue) val).coerceToBool();
                    return new PythonValueSet(bool, crocodile);
                } else if (val instanceof StringValue) {
                    StringValue stringValue = (StringValue) val;
                    return new PythonValueSet(wrap(stringValue.coerceToInt()), crocodile);
                } else if (val instanceof PythonValue) {
                    PythonValue pvalue = (PythonValue) val;
                    return _int(crocodile, pvalue);
                }
                
                return PythonValueSet.EMPTY;
            }
        });
    }
    
    protected PythonValueSet _int(Krocodile crocodile, PythonValue pvalue) {
        PythonValueSet methods = CallResolver.findMethod(pvalue, "__int__", crocodile);
        PythonValueSet results = new PythonValueSet();
        if (methods.isEmpty()) {
            results.addException(new TypeError(pvalue.getType() + " instance has no attribute '__int__'"));
            return results;
        }
        for (PythonObject method : methods) {
            PythonValueSet to_int = CallResolver.callFunction(method, new RuntimeArguments(method),
                    crocodile, getDecl());
            for (PythonObject intvalue : to_int) {
                if (intvalue instanceof IntegerValue || intvalue instanceof LongValue) {
                    results.addResult(intvalue, crocodile);
                } else {
                    PythonType type = intvalue.getType();
                    results.addException(new TypeError("__int__ returned non-int (type " + type + ")"));
                }
                
            }
        }
        return results;
    }
    
    public IntegerValue cast(PythonObject val) throws PythonException {
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
    public IntegerValue coerce(PythonObject val) throws PythonException {
        if (val instanceof IntegerValue)
            return (IntegerValue) val;
        throw new CoersionFailed();
        
    }
}
