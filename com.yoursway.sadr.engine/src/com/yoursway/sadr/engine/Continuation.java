package com.yoursway.sadr.engine;

/**
 * Unit of execution. Every Continuation is designed to be run under scheduler
 * (ContinuationScheduler). Created Continuation is scheduled to run by calling
 * ContinuationRequestor.subgoal() and run in order.
 * 
 * When continuation is scheduled to run, it is asked for sub-continuations
 * (subgoals) by invoking provideSubgoals(). Every sub-continuation is scheduled
 * too, and when all sub-continuations are finished, then done() method is
 * called on Continuation.
 * 
 * ContinuationRequestor passed to done() method may be used to schedule another
 * continuations.
 * 
 */
public interface Continuation {
    
    void provideSubgoals(SubgoalRequestor requestor);
    
    void done(ContinuationScheduler requestor);
}
