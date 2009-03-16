package com.yoursway.sadr.python_v2.model;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonException;

public class RuntimeArguments {
    private final List<PythonObject> args;
    private final HashMap<String, PythonObject> kwargs;
    private PythonObject lastArg;
    private PythonObject lastKwarg;
    
    public RuntimeArguments(List<PythonObject> args, HashMap<String, PythonObject> kwargs,
            PythonObject last_arg, PythonObject last_kwarg) {
        this.args = args;
        this.kwargs = kwargs;
        this.lastArg = last_arg;
        this.lastKwarg = last_kwarg;
    }
    
    public RuntimeArguments() {
        this.args = newArrayList();
        this.kwargs = newHashMap();
        this.lastArg = null;
        this.lastKwarg = null;
    }
    
    public RuntimeArguments(List<PythonObject> args) {
        this.args = args;
        this.kwargs = newHashMap();
        this.lastArg = null;
        this.lastKwarg = null;
    }
    
    public RuntimeArguments(PythonObject... args) {
        this.args = newArrayList();
        this.args.addAll(Arrays.asList(args));
        this.kwargs = newHashMap();
        this.lastArg = null;
        this.lastKwarg = null;
    }
    
    /**
     * @return [a] of (a, b=1, *args, **kwargs)
     */
    public List<PythonObject> getArgs() {
        return args;
    }
    
    /**
     * @return {b=1} of (a, b=1, *args, **kwargs)
     */
    public HashMap<String, PythonObject> getKwargs() {
        return kwargs;
    }
    
    /**
     * @return args of (a, b=1, *args, **kwargs)
     */
    public PythonObject getLastArg() {
        return lastArg;
    }
    
    /**
     * @return kwargs of (a, b=1, *args, **kwargs)
     */
    public PythonObject getLastKwarg() {
        return lastKwarg;
    }
    
    /**
     * Allow to change args of (a, b=1, *args, **kwargs)
     */
    public void setLastArg(PythonObject lastArg) {
        this.lastArg = lastArg;
    }
    
    /**
     * Allow to change kwargs of (a, b=1, *args, **kwargs)
     */
    public void setLastKwarg(PythonObject lastKwarg) {
        this.lastKwarg = lastKwarg;
    }
    
    /**
     * Process arguments to function call
     * 
     * @param count
     *            -- number of arguments to match
     * @throws PythonException
     */
    public List<PythonObject> readArgs(int count) throws TypeError {
        PythonArgumentsReader reader = new PythonArgumentsReader(this);
        List<PythonObject> result = reader.lastArgs();
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
     * 
     * @throws PythonException
     */
    public List<PythonObject> readPositionalArgs() throws TypeError {
        PythonArgumentsReader reader = new PythonArgumentsReader(this);
        if (reader.hasKwargs())
            throw new IllegalStateException("More arguments than required");
        List<PythonObject> result = reader.lastArgs();
        return result;
    }
    
    /**
     * Process arguments to function call
     * 
     * @throws PythonException
     * 
     * @returns single argument
     */
    public <T extends PythonObject> PythonObject readSingle() throws TypeError {
        return this.readArgs(1).get(0);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (PythonObject arg : this.args) {
            builder.append(", " + arg);
        }
        for (Entry<String, PythonObject> arg : this.kwargs.entrySet()) {
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
    
    public void readZero() throws TypeError {
        readArgs(0);
    }
}
