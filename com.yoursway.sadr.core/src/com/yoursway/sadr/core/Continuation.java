package com.yoursway.sadr.core;

public interface Continuation {
    
    void provideSubgoals(SubgoalRequestor requestor);
    
    void done(ContinuationRequestor requestor);
    
}
