package com.yoursway.sadr.engine;

public interface ContinuationRequestor {
    
    void subgoal(Continuation cont);
    
    void done();
    
    Query currentQuery();
    
}
