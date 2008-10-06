package com.yoursway.sadr.core;

import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;

public interface BackwardPropagationEntryPoint {
    
    boolean backwardPropagation(Goal<?> goal, ContinuationScheduler requestor,
            ValueInfoContinuation continuation);
    
}
