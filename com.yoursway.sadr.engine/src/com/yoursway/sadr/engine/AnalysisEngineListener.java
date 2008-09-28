package com.yoursway.sadr.engine;

import com.yoursway.sadr.engine.spi.GoalState;

public interface AnalysisEngineListener {
    
    void cacheHit(GoalState parentState, Goal goal);
    
    void recursiveGoal(GoalState parentState, Goal goal, GoalState duplicateOf);
    
}
