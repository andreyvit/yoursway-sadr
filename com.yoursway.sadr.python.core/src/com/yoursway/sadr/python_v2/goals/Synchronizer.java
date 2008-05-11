package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.succeeder.IGrade;

public class Synchronizer {
    private final GoalSharedData data;
    
    public Synchronizer(int items) {
        this.data = new GoalSharedData(items);
    }
    
    public Synchronizer(GoalSharedData data) {
        this.data = data;
    }
    
    public <T> void subgoalDone(IGrade<T> grade) {
        if (!grade.isDone())
            return;
        if (data.decrement() == 0)
            allDone(grade);
    }
    
    public <T> void allDone(IGrade<T> grade) {
    }
    
}
