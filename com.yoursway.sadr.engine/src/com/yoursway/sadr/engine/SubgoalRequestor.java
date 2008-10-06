package com.yoursway.sadr.engine;

public interface SubgoalRequestor {
    
    <R extends Result> Slot<R> subgoal(Goal<R> goal);
    
}
