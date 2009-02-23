package com.yoursway.sadr.engine.internal;

import com.yoursway.sadr.engine.Goal;
import com.yoursway.utils.Failure;
import com.yoursway.utils.YsDebugging;

public class CachedGoalHasNullResult extends Failure {
    
    private static final long serialVersionUID = 1L;
    
    public CachedGoalHasNullResult(Goal<?> goal) {
        add("goal", YsDebugging.simpleNameOf(goal));
    }
    
}
