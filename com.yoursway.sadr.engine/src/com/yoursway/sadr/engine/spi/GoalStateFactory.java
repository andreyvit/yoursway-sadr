package com.yoursway.sadr.engine.spi;

import com.yoursway.sadr.engine.Goal;

public interface GoalStateFactory {
    
    GoalState createState(GoalState parentState, Goal goal);
    
}
