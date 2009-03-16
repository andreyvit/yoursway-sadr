package com.yoursway.sadr.python.objects;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.yoursway.sadr.python.model.types.CoersionFailed;
import com.yoursway.sadr.python.model.types.StringType;
import com.yoursway.sadr.python.model.values.DictValue;
import com.yoursway.sadr.python.model.values.ListValue;
import com.yoursway.sadr.python.model.values.StringValue;
import com.yoursway.sadr.python.model.values.TupleValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class PythonArgumentsReader {
    private final List<PythonValue> realArgs;
    private final HashMap<String, PythonValue> realKwargs;
    private int argId = 0;
    
    private PythonValue getArg(boolean required) {
        if (argId >= realArgs.size()) {
            if (required)
                throw new IllegalStateException("Not enough arguments");
            else
                return null;
        }
        return realArgs.get(argId++);
    }
    
    public HashMap<String, PythonValue> lastKwargs() {
        return realKwargs;
    }
    
    public List<PythonValue> lastArgs() {
        return realArgs.subList(argId, realArgs.size());
    }
    
    public PythonValue getKwarg(String key, boolean required) {
        if (realKwargs.containsKey(key)) {
            return realKwargs.remove(key);
        }
        return getArg(required);
    }
    
    public PythonArgumentsReader(RuntimeArguments args) throws TypeError {
        realArgs = newArrayList(args.getArgs());
        realKwargs = newHashMap(args.getKwargs());
        for (PythonValue arg : getList(args.getLastArg())) {
            realArgs.add(arg);
        }
        Set<Entry<PythonValue, PythonValue>> items = getDict(args.getLastKwarg()).entrySet();
        for (Entry<PythonValue, PythonValue> entry : items) {
            PythonValue keyObject = entry.getKey();
            try {
                StringValue key = StringType.instance.coerce(keyObject);
                PythonValue value = entry.getValue();
                realKwargs.put(key.value(), value);
            } catch (CoersionFailed e) {
                throw new TypeError("Error: keywords must be string");
            }
        }
    }
    
    private HashMap<PythonValue, PythonValue> getDict(PythonValue dict) {
        if (dict instanceof DictValue)
            return ((DictValue) dict).getDict();
        return newHashMap();
    }
    
    private List<PythonValue> getList(PythonValue list) {
        if (list instanceof ListValue)
            return ((ListValue) list).getList();
        if (list instanceof TupleValue)
            return ((TupleValue) list).getList();
        return newArrayList();
    }
    
    public boolean hasMore() {
        return !hasArgs() && !hasKwargs();
    }
    
    public boolean hasArgs() {
        return argId < realArgs.size();
    }
    
    public boolean hasKwargs() {
        return !realKwargs.isEmpty();
    }
}
