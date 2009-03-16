package com.yoursway.sadr.python.core.runtime;

import com.yoursway.sadr.engine.AbstractGoal;
import com.yoursway.sadr.engine.Goal;

public abstract class AbstractGenericGoal extends AbstractGoal<GenericResult> {
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(getClass().getSimpleName());
        return s.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
    
    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
    
    @Override
    public Goal<GenericResult> cloneGoal() {
        throw new UnsupportedOperationException();
    }
    
    public int debugSlot() {
        return 7;
    }
    
    public GenericResult createRecursiveResult() {
        throw new UnsupportedOperationException();
    }
    
}
