package com.yoursway.sadr.python_v2.model;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.HashMap;
import java.util.List;

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
    
    public PythonArgumentsReader parse() {
        return new PythonArgumentsReader(this);
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
}
