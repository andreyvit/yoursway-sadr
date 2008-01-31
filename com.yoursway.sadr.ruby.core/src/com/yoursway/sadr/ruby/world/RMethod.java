package com.yoursway.sadr.ruby.world;

import com.yoursway.sadr.core.Callable;

public class RMethod extends Callable {
    
    private final String name;
    
    public RMethod(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name + "()";
    }
    
}
