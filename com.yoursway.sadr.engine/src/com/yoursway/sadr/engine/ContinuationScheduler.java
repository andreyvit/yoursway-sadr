package com.yoursway.sadr.engine;

/**
 * Scheduler for tasks (continuations).
 */
public interface ContinuationScheduler {
    
    ContinuationRequestorCalledToken schedule(Continuation cont);
    
    ContinuationRequestorCalledToken schedule(SimpleContinuation cont);
    
    ContinuationRequestorCalledToken done(Result result);
    
    Query currentQuery();
}
