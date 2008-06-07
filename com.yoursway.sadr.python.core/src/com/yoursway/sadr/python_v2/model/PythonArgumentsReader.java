package com.yoursway.sadr.python_v2.model;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python_v2.model.builtins.DictValue;
import com.yoursway.sadr.python_v2.model.builtins.ListValue;
import com.yoursway.sadr.python_v2.model.builtins.TupleValue;

public class PythonArgumentsReader {
    private final List<RuntimeObject> realArgs;
    private final HashMap<String, RuntimeObject> realKwargs;
    private int argId = 0;
    
    private RuntimeObject getArg(boolean required) {
        if (argId >= realArgs.size()) {
            if (required)
                throw new IllegalStateException("Not enough arguments");
            else
                return null;
        }
        return realArgs.get(argId++);
    }
    
    public HashMap<String, RuntimeObject> lastKwargs() {
        return realKwargs;
    }
    
    public List<RuntimeObject> lastArgs() {
        return realArgs.subList(argId, realArgs.size());
    }
    
    public RuntimeObject getKwarg(String key, boolean required) {
        if (realKwargs.containsKey(key)) {
            return realKwargs.remove(key);
        }
        return getArg(required);
    }
    
    public PythonArgumentsReader(PythonArguments args) {
        realArgs = newArrayList(args.getArgs());
        realKwargs = newHashMap(args.getKwargs());
        for (RuntimeObject arg : getList(args.getLastArg())) {
            realArgs.add(arg);
        }
        Set<Entry<RuntimeObject, RuntimeObject>> items = getDict(args.getLastKwarg()).entrySet();
        for (Entry<RuntimeObject, RuntimeObject> entry : items) {
            RuntimeObject keyObject = entry.getKey();
            StringValue key = keyObject.convertValue(StringValue.class);
            if (key == null)
                throw new IllegalStateException("Error: keyword name should be string");
            RuntimeObject value = entry.getValue();
            realKwargs.put(key.value(), value);
        }
    }
    
    private HashMap<RuntimeObject, RuntimeObject> getDict(RuntimeObject dict) {
        if (dict == null)
            return newHashMap();
        DictValue dictValue = dict.convertValue(DictValue.class);
        if (dictValue != null)
            return dictValue.getDict();
        return newHashMap();
    }
    
    private List<RuntimeObject> getList(RuntimeObject list) {
        if (list == null)
            return newArrayList();
        ListValue listValue = list.convertValue(ListValue.class);
        if (listValue != null)
            return listValue.getList();
        TupleValue tupleValue = list.convertValue(TupleValue.class);
        if (tupleValue != null)
            return tupleValue.getList();
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
