package com.yoursway.sadr.core;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;

public interface BackwardPropagationEntryPoint {
    
    boolean backwardPropagation(Goal goal, ContinuationRequestor requestor, ValueInfoContinuation continuation);
    
}
