package com.yoursway.sadr.engine;

public interface Goal<R extends Result> {
    
    ContinuationRequestorCalledToken evaluate(ContinuationScheduler requestor);
    
    boolean cachable();
    
    int debugSlot();
    
    Goal<R> cloneGoal();
    
    boolean hasComplexUnnaturalRelationshipWithRecursion();
    
    R createRecursiveResult();
    
}
