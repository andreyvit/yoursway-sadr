package com.yoursway.sadr.engine;

public interface ContinuationRequestor {
    
    GoalReturnValue subgoal(Continuation cont);
    
    void done();
    
    Query currentQuery();
    
}
