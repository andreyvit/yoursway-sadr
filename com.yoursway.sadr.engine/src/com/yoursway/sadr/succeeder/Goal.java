package com.yoursway.sadr.succeeder;

import com.yoursway.sadr.engine.AbstractGoal;
import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.engine.Result;

public abstract class Goal<R extends Result> extends AbstractGoal<R> {
    
    @Override
    public boolean cachable() {
        return true;
    }
    
    @Override
    public boolean hasComplexUnnaturalRelationshipWithRecursion() {
        return false;
    }
    
    public R createRecursiveResult() {
        return null;
    }
    
    public int debugSlot() {
        return 0;
    }
    
    @Override
    public String toString() {
        String result = describe();
        if (result == null)
            throw new NullPointerException("goal.describe is null");
        return result;
    }
    
    public String describe() {
        String simpleName = this.getClass().getSimpleName();
        if (simpleName.equals("")) {
            simpleName = this.getClass().getName();
            simpleName = simpleName.substring(simpleName.lastIndexOf('.') + 1);
        }
        return simpleName;
    }
    
    public R schedule(Goal<R> goal) {
        return Analysis.evaluate(goal);
    }
}
