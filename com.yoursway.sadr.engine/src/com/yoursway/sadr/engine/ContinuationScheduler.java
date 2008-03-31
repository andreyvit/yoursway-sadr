package com.yoursway.sadr.engine;

/**
 * Scheduler for tasks (continuations).
 */
public interface ContinuationScheduler {
    
    ContinuationRequestorCalledToken schedule(Continuation cont);
    
    ContinuationRequestorCalledToken done();
    
    Query currentQuery();
}
