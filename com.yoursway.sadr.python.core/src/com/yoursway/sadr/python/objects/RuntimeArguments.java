package com.yoursway.sadr.python.objects;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.yoursway.sadr.python.model.types.PythonException;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class RuntimeArguments {
    private final List<PythonValue> args;
    private final HashMap<String, PythonValue> kwargs;
    private PythonValue lastArg;
    private PythonValue lastKwarg;
    
    public RuntimeArguments(List<PythonValue> args, HashMap<String, PythonValue> kwargs,
            PythonValue last_arg, PythonValue last_kwarg) {
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
    
    public RuntimeArguments(List<PythonValue> args) {
        this.args = args;
        this.kwargs = newHashMap();
        this.lastArg = null;
        this.lastKwarg = null;
    }
    
    public RuntimeArguments(PythonValue... args) {
        this.args = newArrayList();
        this.args.addAll(Arrays.asList(args));
        this.kwargs = newHashMap();
        this.lastArg = null;
        this.lastKwarg = null;
    }
    
    /**
     * @return [a] of (a, b=1, *args, **kwargs)
     */
    public List<PythonValue> getArgs() {
        return args;
    }
    
    /**
     * @return {b=1} of (a, b=1, *args, **kwargs)
     */
    public HashMap<String, PythonValue> getKwargs() {
        return kwargs;
    }
    
    /**
     * @return args of (a, b=1, *args, **kwargs)
     */
    public PythonValue getLastArg() {
        return lastArg;
    }
    
    /**
     * @return kwargs of (a, b=1, *args, **kwargs)
     */
    public PythonValue getLastKwarg() {
        return lastKwarg;
    }
    
    /**
     * Allow to change args of (a, b=1, *args, **kwargs)
     */
    public void setLastArg(PythonValue lastArg) {
        this.lastArg = lastArg;
    }
    
    /**
     * Allow to change kwargs of (a, b=1, *args, **kwargs)
     */
    public void setLastKwarg(PythonValue lastKwarg) {
        this.lastKwarg = lastKwarg;
    }
    
    /**
     * Process arguments to function call
     * 
     * @param count
     *            -- number of arguments to match
     * @throws PythonException
     */
    public List<PythonValue> readArgs(int count) throws TypeError {
        PythonArgumentsReader reader = new PythonArgumentsReader(this);
        List<PythonValue> result = reader.lastArgs();
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
    public List<PythonValue> readPositionalArgs() throws TypeError {
        PythonArgumentsReader reader = new PythonArgumentsReader(this);
        if (reader.hasKwargs())
            throw new IllegalStateException("More arguments than required");
        List<PythonValue> result = reader.lastArgs();
        return result;
    }
    
    /**
     * Process arguments to function call
     * 
     * @throws PythonException
     * 
     * @returns single argument
     */
    public <T extends PythonValue> PythonValue readSingle() throws TypeError {
        return this.readArgs(1).get(0);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (PythonValue arg : this.args) {
            builder.append(", " + arg);
        }
        for (Entry<String, PythonValue> arg : this.kwargs.entrySet()) {
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
