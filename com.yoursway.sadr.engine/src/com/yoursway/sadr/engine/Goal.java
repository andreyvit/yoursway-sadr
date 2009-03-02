package com.yoursway.sadr.engine;

import kilim.pausable;

public interface Goal<R extends Result> {
    
    @pausable
    R evaluate();
    
    boolean cachable();
    
    int debugSlot();
    
    Goal<R> cloneGoal();
    
    boolean hasComplexUnnaturalRelationshipWithRecursion();
    
    R createRecursiveResult();
    
}
