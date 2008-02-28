package com.yoursway.sadr.engine;

public interface Continuation {
    
    void provideSubgoals(SubgoalRequestor requestor);
    
    void done(ContinuationRequestor requestor);
    
}
