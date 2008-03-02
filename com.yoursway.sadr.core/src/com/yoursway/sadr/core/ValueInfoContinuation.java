package com.yoursway.sadr.core;

import com.yoursway.sadr.engine.ContinuationRequestor;

public interface ValueInfoContinuation {
    
    void consume(IValueInfo result, ContinuationRequestor requestor);
    
}
