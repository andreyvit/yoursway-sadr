package com.yoursway.sadr.succeeder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

//TODO if there is a goal with all children finished 
//no new goals scheduled then flush.

public class Engine implements IScheduler {
    
    private Map<IAcceptor, IGrade<? extends IGrade<?>>> acceptors = new HashMap<IAcceptor, IGrade<?>>();
    private final TreeMap<Integer, LinkedList<IGoal>> queue = new TreeMap<Integer, LinkedList<IGoal>>();
    private final HashSet<IGoal> allPlannedGoals = new HashSet<IGoal>();
    private final ISchedulingStrategy defaultStrategy;
    private final Map<IGoal, ISchedulingStrategy> goalToStrategy = new HashMap<IGoal, ISchedulingStrategy>();
    private final Map<IGoal, Collection<IGoal>> goalToSubgoals = new HashMap<IGoal, Collection<IGoal>>();
    
    public Engine(ISchedulingStrategy strategy) {
        this.defaultStrategy = strategy;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends IGrade<T>> CheckpointToken checkpoint(IAcceptor acceptor, IGrade<T> grade) {
        T previousGrade = (T) acceptors.get(acceptor);
        if (previousGrade != null && previousGrade.compareTo((T) grade) > 0) {
            throw new IllegalArgumentException("Grade should not decrease for "
                    + acceptor.getClass().getSimpleName());
        }
        acceptors.put(acceptor, grade);
        return CheckpointToken.instance();
    }
    
    public void schedule(IGoal goal, IGoal subgoal) {
        schedule(goal, subgoal, defaultStrategy);
    }
    
    private LinkedList<IGoal> lookupByPriority(int priority) {
        LinkedList<IGoal> linkedList = queue.get(priority);
        if (linkedList == null) {
            linkedList = new LinkedList<IGoal>();
            queue.put(priority, linkedList);
        }
        return linkedList;
    }
    
    public void schedule(IGoal goal, IGoal subgoal, ISchedulingStrategy strategy) {
        if (goal != null) {
            Collection<IGoal> subgoals = goalToSubgoals.get(goal);
            if (subgoals == null) {
                subgoals = new LinkedList<IGoal>();
                goalToSubgoals.put(goal, subgoals);
            }
            subgoals.add(subgoal);
        }
        int priority = strategy.getPriority(subgoal);
        goalToStrategy.put(subgoal, strategy);
        LinkedList<IGoal> thisPriority = lookupByPriority(priority);
        thisPriority.add(subgoal);
        boolean added = allPlannedGoals.add(subgoal);
        if (!added)
            throw new IllegalArgumentException("Trying adding goal twice: " + subgoal.toString());
    }
    
    public void schedule(IGoal goal, Collection<IGoal> subgoals, ISchedulingStrategy strategy) {
        for (IGoal subgoal : subgoals) {
            schedule(goal, subgoal, strategy);
        }
    }
    
    public void run(IGoal goal) {
        schedule(null, goal);
        while (!queue.isEmpty()) {
            passGoals();
            passCheckpoints();
        }
    }
    
    private void passGoals() {
        Integer highest = queue.keySet().iterator().next();
        LinkedList<IGoal> list = lookupByPriority(highest);
        queue.remove(highest);
        for (IGoal goal : list) {
            ISchedulingStrategy strategy = goalToStrategy.remove(goal);
            goal.setScheduler(this);
            if (strategy.prune(goal)) {
                goal.flush();
            } else {
                goal.preRun();
            }
            allPlannedGoals.remove(goal);
        }
    }
    
    private void passCheckpoints() {
        Map<IAcceptor, IGrade<?>> generation = acceptors;
        acceptors = new HashMap<IAcceptor, IGrade<?>>();
        for (IAcceptor acceptor : generation.keySet()) {
            IGrade<?> grade = generation.get(acceptor);
            acceptor.checkpoint(grade);
        }
    }
}
