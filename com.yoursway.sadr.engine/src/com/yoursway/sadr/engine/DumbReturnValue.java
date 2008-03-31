package com.yoursway.sadr.engine;

/**
 * The only way to get an instance of this class is calling API methods.
 */

public final class DumbReturnValue implements ContinuationRequestorCalledToken {
    private DumbReturnValue() {
    }
    
    private static final ContinuationRequestorCalledToken instance = new DumbReturnValue();
    
    static ContinuationRequestorCalledToken instance() {
        return instance;
    }
}
