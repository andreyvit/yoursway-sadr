package com.yoursway.sadr.engine.v1;

import java.util.Arrays;

public class GoalContext {
    
    private final Goal goal;
    private final Result[] weakResult;
    
    public GoalContext(Goal goal, Result[] weakResult) {
        this.goal = goal;
        this.weakResult = weakResult;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((goal == null) ? 0 : goal.hashCode());
        result = prime * result + Arrays.hashCode(weakResult);
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final GoalContext other = (GoalContext) obj;
        if (goal == null) {
            if (other.goal != null)
                return false;
        } else if (!goal.equals(other.goal))
            return false;
        if (!Arrays.equals(weakResult, other.weakResult))
            return false;
        return true;
    }
    
}
