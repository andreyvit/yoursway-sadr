package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;

public interface BackwardPropagationEntryPoint {
    
    boolean backwardPropagation(Goal goal, ContinuationScheduler requestor, ValueInfoContinuation continuation);
    
}
