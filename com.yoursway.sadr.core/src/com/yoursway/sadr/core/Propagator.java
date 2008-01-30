package com.yoursway.sadr.core;

public interface Propagator {

    void propagateToFlow(Goal goal, ContinuationRequestor requestor);

    void propagateToValue(Goal goal, ContinuationRequestor requestor);
    
}
