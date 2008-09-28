package com.yoursway.sadr.engine.spi;

public interface PossibleSubgoalsAdder {
    
    void runAndPossiblyAddSubgoals(GoalState writableGoalState);
    
}