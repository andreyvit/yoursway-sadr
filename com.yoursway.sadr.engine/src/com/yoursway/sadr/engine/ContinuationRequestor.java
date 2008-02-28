package com.yoursway.sadr.engine;

public interface ContinuationRequestor {
    
    void subgoal(Continuation cont);
    
    Query currentQuery();
    
}
