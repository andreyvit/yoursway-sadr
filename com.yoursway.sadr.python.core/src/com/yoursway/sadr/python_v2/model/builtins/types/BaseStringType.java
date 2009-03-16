package com.yoursway.sadr.python_v2.model.builtins.types;

import static com.google.common.collect.Lists.immutableList;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.values.StringValue;

public class BaseStringType extends BuiltinType {
    public static BaseStringType instance = new BaseStringType();
    
    static List<BaseStringType> types = immutableList(UnicodeType.instance, StringType.instance);
    
    @Override
    public StringValue coerce(PythonObject val) throws CoersionFailed {
        if (val instanceof StringValue) {
            return (StringValue) val;
        }
        throw new CoersionFailed();
    }
    
    public static List<StringValue> coerce(int size, RuntimeArguments args) throws PythonException {
        List<PythonObject> values = args.readArgs(size);
        for (BaseStringType type : types) {
            if (hasType(values, type)) {
                List<StringValue> output = new ArrayList<StringValue>(values.size());
                for (PythonObject value : values) {
                    output.add(type.coerce(value));
                }
                return output;
            }
        }
        throw new CoersionFailed();
    }
}
