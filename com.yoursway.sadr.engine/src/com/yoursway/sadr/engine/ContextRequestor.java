package com.yoursway.sadr.engine;

public interface ContextRequestor {
    
    ContinuationRequestorCalledToken execute(GoalContext context, ContinuationScheduler requestor);
    
}
