package com.yoursway.sadr.engine.v1;

public interface IterationContinuation<T> {
    
    ContinuationRequestorCalledToken iteration(T value, ContinuationScheduler requestor,
            SimpleContinuation continuation);
    
}
