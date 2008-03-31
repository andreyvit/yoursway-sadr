package com.yoursway.sadr.engine;

public interface IterationContinuation<T> {
    
    ContinuationRequestorCalledToken iteration(T value, ContinuationScheduler requestor,
            SimpleContinuation continuation);
    
}
