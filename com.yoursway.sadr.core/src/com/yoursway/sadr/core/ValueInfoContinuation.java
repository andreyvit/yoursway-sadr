package com.yoursway.sadr.core;

import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;

public interface ValueInfoContinuation {
    
    ContinuationRequestorCalledToken consume(IValueInfo result, ContinuationScheduler requestor);
    
}
