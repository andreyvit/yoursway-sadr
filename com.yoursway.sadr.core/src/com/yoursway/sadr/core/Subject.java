package com.yoursway.sadr.core;

public interface Subject {
    
    void process(Goal goal, ContinuationRequestor requestor);
    
}
