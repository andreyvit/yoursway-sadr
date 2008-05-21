package com.yoursway.sadr.python_v2.model;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;

public class ArgumentsUtil {
    public static List<RuntimeObject> getArgs(PythonArguments args, int count) {
        PythonArgumentsReader reader = args.parse();
        List<RuntimeObject> result = reader.lastArgs();
        if (result.size() != count) {
            throw new IllegalStateException("Argument number mismatch: " + count + " required, "
                    + result.size() + " given");
        }
        if (reader.hasKwargs())
            throw new IllegalStateException("More arguments than required");
        return result;
    }
    
    public static <T extends Value> List<T> getCastedArgs(PythonArguments args, int count,
            PythonClassType type) {
        List<T> results = newArrayList();
        for (RuntimeObject object : getArgs(args, count)) {
            T value = object.convertValue(type);
            results.add(value);
        }
        return results;
    }
}
