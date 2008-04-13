package com.yoursway.sadr.engine;


public abstract class AbstractGoal extends AbstractPropagatingGoal implements Goal {
    
    @Override
    public abstract String toString();
    
    public void done() {
    }
    
    public boolean cachable() {
        return true;
    }
    
    protected ContextSensitiveThing thing() {
        return this;
    }
    
    public boolean hasComplexUnnaturalRelationshipWithRecursion() {
        return false;
    }
    
}
