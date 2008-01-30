package com.yoursway.sadr.core;

public abstract class AbstractGoal implements Goal {
    
    protected abstract PropagationStyle propagationStyle();

    public void delegatePropagationTo(Propagator propagator, ContinuationRequestor requestor) {
        propagationStyle().delegatePropagation(this, propagator, requestor);
    }
    
}
