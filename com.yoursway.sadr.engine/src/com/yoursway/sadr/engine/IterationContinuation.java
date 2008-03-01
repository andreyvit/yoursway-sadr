package com.yoursway.sadr.engine;

public interface IterationContinuation<T> {
    
    void iteration(T value, ContinuationRequestor requestor, SimpleContinuation continuation);
    
}
