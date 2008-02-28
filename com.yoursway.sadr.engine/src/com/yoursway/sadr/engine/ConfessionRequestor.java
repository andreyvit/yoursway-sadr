package com.yoursway.sadr.engine;


public interface ConfessionRequestor {
    
    void execute(GoalConfession confession, ContinuationRequestor requestor);
    
}
