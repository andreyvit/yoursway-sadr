package com.yoursway.sadr.engine;

import java.util.Arrays;


public class GoalConfession {
    
    private final Goal goal;
    private final Result[] lameExcuses;
    
    public GoalConfession(Goal goal, Result[] lameExcuses) {
        this.goal = goal;
        this.lameExcuses = lameExcuses;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((goal == null) ? 0 : goal.hashCode());
        result = prime * result + Arrays.hashCode(lameExcuses);
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
        final GoalConfession other = (GoalConfession) obj;
        if (goal == null) {
            if (other.goal != null)
                return false;
        } else if (!goal.equals(other.goal))
            return false;
        if (!Arrays.equals(lameExcuses, other.lameExcuses))
            return false;
        return true;
    }
    
}
