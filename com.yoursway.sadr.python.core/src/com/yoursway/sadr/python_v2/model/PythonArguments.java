package com.yoursway.sadr.python_v2.model;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.ArrayList;
import java.util.Arrays;
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
    
    public PythonArguments(RuntimeObject... args) {
        this.args = newArrayList();
        this.args.addAll(Arrays.asList(args));
        this.kwargs = newHashMap();
        this.lastArg = null;
        this.lastKwarg = null;
    }
    
    /**
     * @return [a] of (a, b=1, *args, **kwargs)
     */
    public List<RuntimeObject> getArgs() {
        return args;
    }
    
    /**
     * @return {b=1} of (a, b=1, *args, **kwargs)
     */
    public HashMap<String, RuntimeObject> getKwargs() {
        return kwargs;
    }
    
    /**
     * @return args of (a, b=1, *args, **kwargs)
     */
    public RuntimeObject getLastArg() {
        return lastArg;
    }
    
    /**
     * @return kwargs of (a, b=1, *args, **kwargs)
     */
    public RuntimeObject getLastKwarg() {
        return lastKwarg;
    }
    
    /**
     * Allow to change args of (a, b=1, *args, **kwargs)
     */
    public void setLastArg(RuntimeObject lastArg) {
        this.lastArg = lastArg;
    }
    
    /**
     * Allow to change kwargs of (a, b=1, *args, **kwargs)
     */
    public void setLastKwarg(RuntimeObject lastKwarg) {
        this.lastKwarg = lastKwarg;
    }
    
    /**
     * Process arguments to function call
     * 
     * @param count --
     *            number of arguments to match
     */
    public List<RuntimeObject> readArgs(int count) {
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
    
    /**
     * Process arguments to function call.
     * 
     * Any keyword argument will lead to an error
     */
    public List<RuntimeObject> readPositionalArgs() {
        PythonArgumentsReader reader = new PythonArgumentsReader(this);
        if (reader.hasKwargs())
            throw new IllegalStateException("More arguments than required");
        List<RuntimeObject> result = reader.lastArgs();
        return result;
    }
    
    /**
     * Process arguments to function call, and casts them to use type
     * 
     * @param count --
     *            number of arguments to match
     */
    public <T extends Value> List<T> castArgs(int count, PythonClassType type) {
        List<T> results = new ArrayList<T>();
        for (RuntimeObject object : this.readArgs(count)) {
            T value = object.convertValue(type);
            results.add(value);
        }
        return results;
    }
    
    /**
     * Process arguments to function call, and cast them
     * 
     * @returns casted single argument
     */
    public <T extends Value> T castSingle(PythonClassType type) {
        RuntimeObject arg = this.readArgs(1).get(0);
        T value = arg.convertValue(type);
        return value;
    }
    
    /**
     * Process arguments to function call
     * 
     * @returns single argument
     */
    public <T extends Value> RuntimeObject readSingle() {
        return this.readArgs(1).get(0);
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
        if (builder.length() == 0)
            return "empty";
        return builder.substring(2);
    }
}
