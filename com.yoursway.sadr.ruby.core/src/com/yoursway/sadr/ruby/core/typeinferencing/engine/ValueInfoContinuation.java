package com.yoursway.sadr.ruby.core.typeinferencing.engine;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;

public interface ValueInfoContinuation {
    
    void consume(ValueInfo result, ContinuationRequestor requestor);
    
}
