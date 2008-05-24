package com.yoursway.sadr.python_v2.model;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;

public class PythonArguments {
    private final List<RuntimeObject> args;
    private final HashMap<String, RuntimeObject> kwargs;
    private RuntimeObject lastArg;
    private RuntimeObject lastKwarg;
    
    public PythonArguments(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs,
            RuntimeObject last_arg, RuntimeObject last_kwarg) {
        this.args = args;
        this.kwargs = kwargs;
        this.lastArg = last_arg;
        this.lastKwarg = last_kwarg;
    }
    
    public PythonArguments() {
        this.args = newArrayList();
        this.kwargs = newHashMap();
        this.lastArg = null;
        this.lastKwarg = null;
    }
    
    public PythonArguments(List<RuntimeObject> args) {
        this.args = args;
        this.kwargs = newHashMap();
        this.lastArg = null;
        this.lastKwarg = null;
    }
    
    public List<RuntimeObject> getArgs() {
        return args;
    }
    
    public HashMap<String, RuntimeObject> getKwargs() {
        return kwargs;
    }
    
    public RuntimeObject getLastArg() {
        return lastArg;
    }
    
    public RuntimeObject getLastKwarg() {
        return lastKwarg;
    }
    
    public void setLastArg(RuntimeObject lastArg) {
        this.lastArg = lastArg;
    }
    
    public void setLastKwarg(RuntimeObject lastKwarg) {
        this.lastKwarg = lastKwarg;
    }
    
    public List<RuntimeObject> getArgs(int count) {
        PythonArgumentsReader reader = new PythonArgumentsReader(this);
        List<RuntimeObject> result = reader.lastArgs();
        if (result.size() != count) {
            throw new IllegalStateException("Argument number mismatch: " + count + " required, "
                    + result.size() + " given");
        }
        if (reader.hasKwargs())
            throw new IllegalStateException("More arguments than required");
        return result;
    }
    
    public <T extends Value> List<T> castArgs(int count, PythonClassType type) {
        List<T> results = new ArrayList<T>();
        for (RuntimeObject object : this.getArgs(count)) {
            T value = object.convertValue(type);
            results.add(value);
        }
        return results;
    }
    
    public <T extends Value> T castSingle(PythonClassType type) {
        RuntimeObject arg = this.getArgs(1).get(0);
        T value = arg.convertValue(type);
        return value;
    }
    
    public <T extends Value> RuntimeObject getSingle() {
        return this.getArgs(1).get(0);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (RuntimeObject arg : this.args) {
            builder.append(", " + arg);
        }
        for (Entry<String, RuntimeObject> arg : this.kwargs.entrySet()) {
            builder.append(", " + arg.getKey() + "=" + arg.getValue());
        }
        if (this.lastArg != null) {
            builder.append(", *" + this.lastArg);
        }
        if (this.lastKwarg != null) {
            builder.append(", **" + this.lastKwarg);
        }
        return builder.substring(2);
    }
}
