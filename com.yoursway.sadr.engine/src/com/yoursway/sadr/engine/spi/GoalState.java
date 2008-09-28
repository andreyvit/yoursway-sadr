package com.yoursway.sadr.engine.spi;

import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.Query;

public class GoalState {
    
    private static final int DONE_COUNT = -1;
    
    private static final int ADDING_SUBGOALS = 1000000;
    
    public final GoalState parentGoal;
    
    public final Goal goal;
    
    /**
     * Either a count of subgoals, or <code>DONE_COUNT</code> when this goal is
     * done, or <code>ADDING_SUBGOALS</code> plus a count of subgoals during
     * execution of <code>allowStateChangeAndRun</code>.
     */
    private int subgoalCount = 0;
    
    private Query allChildrenFinishedCallback;
    
    private final GoalOwner engine;
    
    // (subgoalCount > 0) <=> (job != null)
    
    public GoalState(GoalOwner engine, Goal goal) {
        if (engine == null)
            throw new NullPointerException("engine is null");
        if (goal == null)
            throw new NullPointerException("goal is null");
        this.engine = engine;
        this.parentGoal = null;
        this.goal = goal;
        engine.startingGoal(this);
    }
    
    public GoalState(GoalOwner engine, GoalState parentGoal, Goal goal) {
        if (engine == null)
            throw new NullPointerException("engine is null");
        if (parentGoal == null)
            throw new NullPointerException("parentGoal is null");
        if (goal == null)
            throw new NullPointerException("goal is null");
        this.engine = engine;
        this.parentGoal = parentGoal;
        this.goal = goal;
        parentGoal.subgoalAdded();
        engine.startingGoal(this);
    }
    
    public void allowStateChangeAndRun(PossibleSubgoalsAdder adder, Query allChildrenFinishedCallback) {
        if (allChildrenFinishedCallback == null)
            throw new NullPointerException("allChildrenFinishedCallback is null");
        
        if (subgoalCount != 0)
            if (subgoalCount == DONE_COUNT)
                throw new IllegalStateException("Cannot allow changing state when the goal is already done");
            else if (subgoalCount >= ADDING_SUBGOALS)
                throw new IllegalStateException("Recursive calls to allowStateChangeAndRun are not allowed");
            else if (subgoalCount > 0)
                throw new IllegalStateException("Cannot allow changing state when there are pending subgoals");
            else
                throw new AssertionError("Invalid value of the internal subgoal count");
        subgoalCount = ADDING_SUBGOALS;
        try {
            adder.runAndPossiblyAddSubgoals(this);
        } finally {
            if (subgoalCount >= ADDING_SUBGOALS)
                subgoalCount -= ADDING_SUBGOALS;
            if (subgoalCount == 0)
                engine.enqueueComputation(allChildrenFinishedCallback);
            else if (subgoalCount > 0)
                this.allChildrenFinishedCallback = allChildrenFinishedCallback;
            else
                throw new AssertionError(
                        "Invalid value of the internal subgoal count (after runAndPossiblyAddSubgoals)");
        }
        
    }
    
    public void runOnBehalfOfThisGoal(Runnable runnable) {
        runnable.run();
    }
    
    public void markAsDone() {
        if (subgoalCount >= ADDING_SUBGOALS)
            throw new IllegalStateException("Cannot mark the goal as done from inside allowStateChangeAndRun");
        if (subgoalCount > 0)
            throw new IllegalStateException("Cannot mark the goal as done when there are pending subgoals");
        subgoalCount = DONE_COUNT;
        goalFinished();
    }
    
    private void goalFinished() {
        //            System.out.println("FINISHED " + goal);
        if (parentGoal != null)
            parentGoal.subgoalFinished(goal);
        try {
            goal.done();
        } finally {
            engine.finishedGoal(this);
        }
    }
    
    void subgoalAdded() {
        if (subgoalCount < ADDING_SUBGOALS)
            if (subgoalCount == DONE_COUNT)
                throw new IllegalStateException("Cannot add subgoals when the goal is done");
            else
                throw new IllegalStateException("Cannot add subgoals from outside allowStateChangeAndRun");
        ++subgoalCount;
    }
    
    void subgoalFinished(Goal subgoal) {
        if (subgoalCount == DONE_COUNT)
            throw new IllegalStateException("Subgoal cannot be finished when the goal is already done");
        if (subgoalCount == 0)
            throw new IllegalStateException("This goal has no subgoals");
        if (--subgoalCount == 0) {
            try {
                engine.enqueueComputation(allChildrenFinishedCallback);
            } finally {
                allChildrenFinishedCallback = null;
            }
        }
    }
    
    @Override
    public String toString() {
        return "<" + subgoalCount + ">" + goal;
    }
    
    public GoalState findStateByGoal(Goal goal) {
        for (GoalState state = this; state != null; state = state.parentGoal)
            if (state.goal.equals(goal))
                return state;
        return null;
    }
    
}