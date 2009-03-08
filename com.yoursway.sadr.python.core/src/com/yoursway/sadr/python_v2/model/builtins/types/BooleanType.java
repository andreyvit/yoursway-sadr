package com.yoursway.sadr.python_v2.model.builtins.types;

import com.yoursway.sadr.python_v2.constructs.BooleanLiteralC;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.CallResolver;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.TypeError;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.values.BooleanValue;
import com.yoursway.sadr.python_v2.model.builtins.values.BuiltinFunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.values.IntegerValue;
import com.yoursway.sadr.python_v2.model.builtins.values.NoneValue;
import com.yoursway.sadr.python_v2.model.builtins.values.NumericValue;
import com.yoursway.sadr.python_v2.model.builtins.values.StringValue;

public class BooleanType extends NumericType {
    @Override
    public PythonObject __nonzero__(RuntimeArguments args) throws PythonException {
        PythonObject val = args.readSingle();
        if (val instanceof PythonValue)
            return cast(val);
        else
            return null;
    }
    
    public static BooleanValue cast(PythonObject value) throws PythonException {
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
    
    private PythonValueSet bool(Krocodile crocodile, PythonValue pvalue) {
        PythonValueSet methods = CallResolver.findMethod(pvalue, "__nonzero__", crocodile);
        if (methods.isEmpty()) {
            methods = CallResolver.findMethod(pvalue, "__len__", crocodile);
        }
        if (methods.isEmpty()) {
            boolean bool = !pvalue.equals(NoneValue.instance);
            return new PythonValueSet(bool, crocodile);
        }
        PythonValueSet results = new PythonValueSet();
        for (PythonObject method : methods) {
            PythonValueSet nonzero = CallResolver.callFunction(method, new RuntimeArguments(method),
                    crocodile, getDecl());
            for (PythonObject nzvalue : nonzero) {
                if (nzvalue instanceof IntegerValue) {
                    long value = ((IntegerValue) nzvalue).value();
                    if (value >= 0) {
                        results.addResult(value != 0, crocodile);
                    } else {
                        results.addException(new TypeError("__nonzero__ should return >= 0"));
                    }
                } else {
                    results.addException(new TypeError("__nonzero__ should return an int"));
                }
                
            }
        }
        return results;
    }
    
    private BooleanType() {
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
                    return new PythonValueSet(BooleanValue.instance_false, crocodile);
                } else if (val instanceof NumericValue) {
                    boolean bool = ((NumericValue) val).coerceToBool();
                    return new PythonValueSet(BooleanValue.instance(bool), crocodile);
                } else if (val instanceof PythonValue) {
                    PythonValue pvalue = (PythonValue) val;
                    return bool(crocodile, pvalue);
                }
                
                return PythonValueSet.EMPTY;
            }
        });
    }
    
    public static BooleanType instance = new BooleanType();
    
    public static BooleanValue wrap(BooleanLiteralC literal) {
        return BooleanValue.instance(literal.getValue());
    }
    
    public static BooleanValue wrap(boolean value) {
        return (value) ? BooleanValue.instance_true : BooleanValue.instance_false;
    }
    
    @Override
    public String describe() {
        return "bool";
    }
    
    @Override
    public BooleanValue coerce(PythonObject val) throws PythonException {
        if (val instanceof BooleanValue)
            return (BooleanValue) val;
        throw new PythonException();
    }
}
