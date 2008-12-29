package com.yoursway.sadr.engine;

public abstract class AbstractGoal<R extends Result> implements Goal<R> {
    
    @Override
    public abstract String toString();
    
    public boolean cachable() {
        return true;
    }
    
    public boolean hasComplexUnnaturalRelationshipWithRecursion() {
        return false;
    }
    
}
