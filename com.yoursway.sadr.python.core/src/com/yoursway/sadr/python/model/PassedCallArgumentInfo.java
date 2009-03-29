package com.yoursway.sadr.python.model;

import com.yoursway.sadr.python.constructs.PythonConstruct;

public abstract class PassedCallArgumentInfo extends PassedArgumentInfo {
    
    protected final PythonConstruct callable;
    
    public PassedCallArgumentInfo(PythonConstruct callable) {
        this.callable = callable;
    }
    
    @Override
    public final PythonConstruct getCallable() {
        return callable;
    }
    
}
