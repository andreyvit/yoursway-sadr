package com.yoursway.sadr.engine.internal;

import com.yoursway.sadr.engine.Goal;
import com.yoursway.utils.Failure;
import com.yoursway.utils.YsDebugging;

public class GoalEvaluationFailed extends Failure {
    
    private static final long serialVersionUID = 1L;
    
    public GoalEvaluationFailed(Throwable e, Goal<?> goal) {
        super(e);
        add("goal_class", YsDebugging.simpleNameOf(goal));
        add("goal", goal.toString());
    }
    
}
