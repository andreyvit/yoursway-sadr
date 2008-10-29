package com.yoursway.sadr.engine.incremental;

import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.Result;

public interface EngineListener {
    
    void goalScheduled(Goal<?> goal);
    
    void goalParentAdded(Goal<?> goal, Goal<?> parent);
    
    void goalFinished(Goal<?> goal, Boolean isRecursive, Result result);
    
    void goalExecutionStarting(Goal<?> goal);
    
    void rootGoalFinished(Goal<?> goal);
    
    void goalRecursiveDependencyAdded(Goal<?> goal, Goal<?> dependsOn);
    
}
