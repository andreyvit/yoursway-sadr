package com.yoursway.sadr.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.yoursway.sadr.engine.util.Lists;

public class Karma {
    
    private static final Karma EMPTY = new Karma(Collections.<Goal> emptyList());
    
    public static final Karma empty() {
        return EMPTY;
    }
    
    private final List<Goal> sins;
    
    public Karma(Collection<Goal> sins) {
        this.sins = new ArrayList<Goal>(sins);
    }
    
    public boolean isEmpty() {
        return sins.isEmpty();
    }
    
    @Override
    public String toString() {
        return sins.toString();
    }
    
    public GoalConfession createPrimaryConfession(Goal goal) {
        Result[] excuses = new Result[sins.size()];
        for (int i = 0; i < excuses.length; i++)
            excuses[i] = sins.get(i).resultWithoutKarma();
        return new GoalConfession(goal, excuses);
    }
    
    public void createSecondaryConfession(final Goal goal, ContinuationRequestor requestor,
            final ConfessionRequestor continuation) {
        requestor.subgoal(new Continuation() {
            
            List<Goal> newGoals = new ArrayList<Goal>(sins.size());
            
            {
                for (Goal sin : sins)
                    if (!(sin.equals(goal)))
                        newGoals.add(sin.cloneGoal());
            }
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                for (Goal goal : newGoals)
                    requestor.subgoal(goal);
            }
            
            public void done(ContinuationRequestor requestor) {
                Result[] excuses = new Result[newGoals.size()];
                for (int i = 0; i < excuses.length; i++)
                    excuses[i] = newGoals.get(i).resultWithoutKarma();
                continuation.execute(new GoalConfession(goal, excuses), requestor);
            }
            
        });
    }
    
    public Karma takeMoreBlame(Karma karma) {
        return new Karma(Lists.combineSets(sins, karma.sins));
    }
    
    public void putBlameOn(Goal goal) {
        goal.blame(sins);
    }
    
}
