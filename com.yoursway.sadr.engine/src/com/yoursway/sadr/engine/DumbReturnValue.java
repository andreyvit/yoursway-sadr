package com.yoursway.sadr.engine;

/**
 * The only way to get an instance of this class is calling API methods.
 */

public final class DumbReturnValue {
    private DumbReturnValue() {
    }
    
    private static final DumbReturnValue instance = new DumbReturnValue();
    
    static DumbReturnValue instance() {
        return instance;
    }
}
