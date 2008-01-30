package com.yoursway.sadr.core;

public interface ContinuationRequestor {
    
    void subgoal(Continuation cont);
    
    void propagate(Subject subject);
    
}
