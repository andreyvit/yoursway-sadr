package com.yoursway.sadr.engine.spi;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.Query;

public class GoalEngineImpl implements GoalOwner, GoalEngine {
    
    private final Map<Goal, GoalState> goalsToStates = newHashMap();
    private final AnalysisComputer queue;
    
    public GoalEngineImpl(AnalysisComputer queue) {
        if (queue == null)
            throw new NullPointerException("queue is null");
        this.queue = queue;
    }
    
    public void enqueueComputation(Query query) {
        queue.enqueueComputation(query);
    }
    
    public void startingGoal(GoalState state) {
        GoalState oldState = goalsToStates.put(state.goal, state);
        if (oldState != null)
            throw new AssertionError();
    }
    
    public void finishedGoal(GoalState state) {
        GoalState removedState = goalsToStates.remove(state.goal);
        if (removedState == null || removedState != state)
            throw new AssertionError();
    }
    
    public GoalState createState(GoalState parentState, Goal goal) {
        GoalState result = goalsToStates.get(goal);
        if (result == null) {
            result = new GoalState(this, parentState, goal);
            goalsToStates.put(goal, result);
        }
        return result;
    }
    
}
