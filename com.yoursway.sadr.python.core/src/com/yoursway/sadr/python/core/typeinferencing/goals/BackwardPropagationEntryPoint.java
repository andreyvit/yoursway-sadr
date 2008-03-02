package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;

public interface BackwardPropagationEntryPoint {
    
    boolean backwardPropagation(Goal goal, ContinuationRequestor requestor, ValueInfoContinuation continuation);
    
}
