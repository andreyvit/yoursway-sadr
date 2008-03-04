package com.yoursway.sadr.python.core.runtime;

public class PythonCallableArgument {
    
    public enum Usage {
        
        REQUIRED,

        OPTIONAL
        
        //        ELLIPSIS
        
    }
    
    private final String name;
    int index;
    private final Usage usage;
    
    public PythonCallableArgument(String name, Usage usage) {
        this.name = name;
        this.usage = usage;
    }
    
    public String name() {
        return name;
    }
    
    public Usage usage() {
        return usage;
    }
    
    @Override
    public String toString() {
        return "&" + name;
    }
    
    public int index() {
        return index;
    }
    
}
