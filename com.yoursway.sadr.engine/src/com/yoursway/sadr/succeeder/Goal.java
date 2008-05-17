package com.yoursway.sadr.succeeder;

import java.util.Collection;

public abstract class Goal implements IGoal {
    private IScheduler scheduler;
    
    protected IScheduler scheduler() {
        return scheduler;
    }
    
    public void schedule(IGoal goal) {
        scheduler.schedule(this, goal);
    }
    
    protected void schedule(Collection<IGoal> goals) {
        scheduler.schedule(this, goals);
    }
    
    public void schedule(IGoal goal, ISchedulingStrategy strategy) {
        scheduler.schedule(this, goal, strategy);
    }
    
    protected void schedule(Collection<IGoal> goals, ISchedulingStrategy strategy) {
        scheduler.schedule(this, goals, strategy);
    }
    
    public <T> CheckpointToken updateGrade(IAcceptor acceptor, IGrade<T> grade) {
        return scheduler.updateGrade(this, acceptor, grade);
    }
    
    public final void setScheduler(IScheduler scheduler) {
        this.scheduler = scheduler;
    }
    
    public CheckpointToken flush() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String toString() {
        String result = describe();
        if (result == null) {
            String simpleName = this.getClass().getSimpleName();
            if (simpleName.equals("")) {
                simpleName = this.getClass().getName();
                simpleName = simpleName.substring(simpleName.lastIndexOf('.') + 1);
            }
            return simpleName;
        } else {
            return result;
        }
    }
    
    abstract protected String describe();
}
