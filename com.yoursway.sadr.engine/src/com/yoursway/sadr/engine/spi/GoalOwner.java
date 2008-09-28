package com.yoursway.sadr.engine.spi;

public interface GoalOwner extends AnalysisComputer {
    
    void startingGoal(GoalState state);
    
    void finishedGoal(GoalState state);
    
}