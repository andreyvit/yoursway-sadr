/**
 * 
 */
package com.yoursway.sadr.engine;

final class DummyGoal extends AbstractGoal {
    
    @Override
    public boolean equals(Object obj) {
        return false;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    
    public void causesRecursion() {
    }
    
    public Goal cloneGoal() {
        return null;
    }
    
    public void copyAnswerFrom(Result result) {
    }
    
    public void copyAnswerFrom(Goal goal) {
    }
    
    public int debugSlot() {
        return 0;
    }
    
    public void evaluate(ContinuationRequestor requestor) {
    }
    
    public Result roughResult() {
        return null;
    }
    
}
