/**
 * 
 */
package com.yoursway.sadr.succeeder;

import java.util.HashSet;
import java.util.Set;

class SharedData {
    
    private final Runnable doneHandler;
    private final Set<Goal> uncompletedGoals = new HashSet<Goal>();
    
    public SharedData(Runnable doneHandler) {
        this.doneHandler = doneHandler;
    }
    
    public synchronized void increment(Goal goal) {
        if (childrenCount < 0)
            childrenCount = 0;
        System.out.println("Reentering task " + goal.toString());
        ++childrenCount;
        boolean added = uncompletedGoals.add(goal);
        if (!added)
            throw new IllegalStateException("Task added twice!!!");
    }
    
    public synchronized void decrement(Goal goal) {
        boolean removed = uncompletedGoals.remove(goal);
        if (!removed)
            throw new IllegalStateException("No such task added before!");
        if (childrenCount <= 0)
            throw new IllegalStateException("Decrement but no subtasks!");
        if (--childrenCount == 0)
            dispose();
    }
    
    protected synchronized void dispose() {
        if (childrenCount < 0)
            throw new IllegalStateException("Task was already completed!");
        if (childrenCount == 0) {
            doneHandler.run();
            childrenCount = -1;
        }
    }
    
    private volatile int childrenCount = 0;
}