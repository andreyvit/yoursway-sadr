package com.yoursway.sadr.python.analysis.index.data;

import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;

public abstract class PassedCallArgumentInfo extends PassedArgumentInfo {
    
    protected final Bnode callable;
    
    public PassedCallArgumentInfo(Bnode callable) {
        this.callable = callable;
    }
    
    @Override
    public final Bnode getCallable() {
        return callable;
    }
    
}
