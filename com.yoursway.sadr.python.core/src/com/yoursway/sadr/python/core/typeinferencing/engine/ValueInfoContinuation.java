package com.yoursway.sadr.python.core.typeinferencing.engine;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;

public interface ValueInfoContinuation {
    
    void consume(ValueInfo result, ContinuationRequestor requestor);
    
}
