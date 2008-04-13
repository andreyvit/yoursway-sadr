package com.yoursway.sadr.engine.v1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.yoursway.sadr.engine.util.Lists;

public class ContextRelation {
    
    private static final ContextRelation EMPTY = new ContextRelation(Collections.<Goal> emptyList());
    
    public static final ContextRelation empty() {
        return EMPTY;
    }
    
    private final List<Goal> goals;
    
    public ContextRelation(Collection<Goal> goals) {
        this.goals = new ArrayList<Goal>(goals);
    }
    
    public boolean isEmpty() {
        return goals.isEmpty();
    }
    
    @Override
    public String toString() {
        return goals.toString();
    }
    
    public GoalContext createPrimaryContext(Goal goal) {
        Result[] results = new Result[goals.size()];
        for (int i = 0; i < results.length; i++)
            results[i] = goals.get(i).roughResult();
        return new GoalContext(goal, results);
    }
    
    public void createSecondaryContext(final Goal goal, ContinuationScheduler requestor,
            final ContextRequestor continuation) {
        requestor.schedule(new Continuation() {
            
            List<Goal> newGoals = new ArrayList<Goal>(goals.size());
            
            {
                for (Goal eachGoal : goals)
                    if (!(eachGoal.equals(goal)))
                        newGoals.add(eachGoal.cloneGoal());
            }
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                for (Goal goal : newGoals)
                    requestor.subgoal(goal);
            }
            
            public void done(ContinuationScheduler requestor) {
                Result[] results = new Result[newGoals.size()];
                for (int i = 0; i < results.length; i++)
                    results[i] = newGoals.get(i).roughResult();
                continuation.execute(new GoalContext(goal, results), requestor);
            }
            
        });
    }
    
    public ContextRelation extend(ContextRelation dependence) {
        return new ContextRelation(Lists.combineSets(goals, dependence.goals));
    }
    
    public void addTo(Goal goal) {
        goal.addPropagatingGoals(goals);
    }
    
}
