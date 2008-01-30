package com.yoursway.sadr.core;

public interface Goal {

    void delegatePropagationTo(Propagator propagator, ContinuationRequestor requestor);
    
}
