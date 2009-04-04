package com.yoursway.sadr.python.analysis.objectmodel.types;

import static com.google.common.collect.Lists.immutableList;

import java.util.List;

import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.StringValue;

public class BaseStringType extends BuiltinType {
    public static BaseStringType instance = new BaseStringType();
    
    static List<BaseStringType> types = immutableList(UnicodeType.instance, StringType.instance);
    
    @Override
    public StringValue coerce(PythonValue val) throws CoersionFailed {
        if (val instanceof StringValue) {
            return (StringValue) val;
        }
        throw new CoersionFailed();
    }
    
    //    public static List<StringValue> coerce(int size, RuntimeArguments args) throws PythonException {
    //        List<PythonValue> values = args.readArgs(size);
    //        for (BaseStringType type : types) {
    //            if (type.hasType(values)) {
    //                List<StringValue> output = new ArrayList<StringValue>(values.size());
    //                for (PythonValue value : values) {
    //                    output.add(type.coerce(value));
    //                }
    //                return output;
    //            }
    //        }
    //        throw new CoersionFailed();
    //    }
}
