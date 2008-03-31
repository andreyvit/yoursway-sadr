package com.yoursway.sadr.engine;

public interface ContextRequestor {
    
    void execute(GoalContext context, ContinuationScheduler requestor);
    
}
