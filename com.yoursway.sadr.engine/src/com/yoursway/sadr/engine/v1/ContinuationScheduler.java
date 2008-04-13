package com.yoursway.sadr.engine.v1;

/**
 * Scheduler for tasks (continuations).
 */
public interface ContinuationScheduler {
    
    ContinuationRequestorCalledToken schedule(Continuation cont);
    
    ContinuationRequestorCalledToken schedule(SimpleContinuation cont);
    
    ContinuationRequestorCalledToken done();
    
    Query currentQuery();
}
