package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.python.core.typeinferencing.engine.ValueInfoContinuation;

public interface BackwardPropagationEntryPoint {
    
    boolean backwardPropagation(Goal goal, ContinuationRequestor requestor, ValueInfoContinuation continuation);
    
}
