package com.yoursway.sadr.engine.v1;

public interface ContextRequestor {
    
    void execute(GoalContext context, ContinuationScheduler requestor);
    
}
