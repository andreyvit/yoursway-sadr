package com.yoursway.sadr.engine;

public interface ContinuationRequestor {
    
    DumbReturnValue subgoal(Continuation cont);
    
    void done();
    
    Query currentQuery();
    
}
