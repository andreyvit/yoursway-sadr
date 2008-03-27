package com.yoursway.sadr.engine;

/**
 * @author buriy
 * 
 * This thing allows to schedule new subgoals
 * 
 * TODO: Rename to Scheduler function
 */
public interface ContinuationRequestor {
    
    /**
     * TODO: Rename to schedule
     */
    DumbReturnValue subgoal(Continuation cont);
    
    void done();
    
    Query currentQuery();
    
}
