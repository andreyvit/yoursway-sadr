package com.yoursway.sadr.succeeder;

import java.util.Collection;

/**
 * 
 */
public abstract class Goal implements IGoal {
    
    private IScheduler scheduler;
    
    protected IScheduler scheduler() {
        return scheduler;
    }
    
    protected void schedule(IGoal goal) {
        scheduler.schedule(this, goal);
    }
    
    protected void schedule(IGoal goal, ISchedulingStrategy strategy) {
        scheduler.schedule(this, goal, strategy);
    }
    
    protected void schedule(Collection<IGoal> goals, ISchedulingStrategy strategy) {
        scheduler.schedule(this, goals, strategy);
    }
    
    public <T> CheckpointToken checkpoint(IAcceptor acceptor, IGrade<T> grade) {
        return scheduler.checkpoint(acceptor, grade);
    }
    
    public final void setScheduler(IScheduler scheduler) {
        this.scheduler = scheduler;
    }
    
    public CheckpointToken flush() {
        //TEMPORARILY children flush is not required
        //remove this method to re-require
        return null;
    }
}
