package com.yoursway.sadr.python_v2.model;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.types.CoersionFailed;
import com.yoursway.sadr.python_v2.model.builtins.types.StringType;
import com.yoursway.sadr.python_v2.model.builtins.values.DictValue;
import com.yoursway.sadr.python_v2.model.builtins.values.ListValue;
import com.yoursway.sadr.python_v2.model.builtins.values.StringValue;
import com.yoursway.sadr.python_v2.model.builtins.values.TupleValue;

public class PythonArgumentsReader {
    private final List<PythonObject> realArgs;
    private final HashMap<String, PythonObject> realKwargs;
    private int argId = 0;
    
    private PythonObject getArg(boolean required) {
        if (argId >= realArgs.size()) {
            if (required)
                throw new IllegalStateException("Not enough arguments");
            else
                return null;
        }
        return realArgs.get(argId++);
    }
    
    public HashMap<String, PythonObject> lastKwargs() {
        return realKwargs;
    }
    
    public List<PythonObject> lastArgs() {
        return realArgs.subList(argId, realArgs.size());
    }
    
    public PythonObject getKwarg(String key, boolean required) {
        if (realKwargs.containsKey(key)) {
            return realKwargs.remove(key);
        }
        return getArg(required);
    }
    
    public PythonArgumentsReader(RuntimeArguments args) throws TypeError {
        realArgs = newArrayList(args.getArgs());
        realKwargs = newHashMap(args.getKwargs());
        for (PythonObject arg : getList(args.getLastArg())) {
            realArgs.add(arg);
        }
        Set<Entry<PythonObject, PythonObject>> items = getDict(args.getLastKwarg()).entrySet();
        for (Entry<PythonObject, PythonObject> entry : items) {
            PythonObject keyObject = entry.getKey();
            try {
                StringValue key = StringType.instance.coerce(keyObject);
                PythonObject value = entry.getValue();
                realKwargs.put(key.value(), value);
            } catch (CoersionFailed e) {
                throw new TypeError("Error: keywords must be string");
            }
        }
    }
    
    private HashMap<PythonObject, PythonObject> getDict(PythonObject dict) {
        if (dict instanceof DictValue)
            return ((DictValue) dict).getDict();
        return newHashMap();
    }
    
    private List<PythonObject> getList(PythonObject list) {
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
