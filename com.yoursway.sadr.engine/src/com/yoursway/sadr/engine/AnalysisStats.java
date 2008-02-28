package com.yoursway.sadr.engine;

import java.util.HashMap;
import java.util.Map;

public class AnalysisStats {
    
    private int totalGoals = 0, recursiveGoals = 0, totalQueries = 0;
    
    private int goalCacheHits = 0, goalCacheMisses = 0;
    
    private final GoalTypeStats[] slots = new GoalTypeStats[7];
    
    private final Map<Goal, Long> startTimes = new HashMap<Goal, Long>();
    
    public AnalysisStats() {
        for (int i = 0; i < slots.length; i++)
            slots[i] = new GoalTypeStats();
    }
    
    public void recursiveGoal(Goal goal) {
        totalGoals++;
        recursiveGoals++;
    }
    
    public void cacheHit(Goal goal) {
        totalGoals++;
        goalCacheHits++;
        slots[goal.debugSlot()].cacheHit();
    }
    
    public void calculatedGoal(Goal goal) {
        totalGoals++;
        goalCacheMisses++;
        slots[goal.debugSlot()].calculatedGoal();
    }
    
    public void finishedQuery(Goal goal) {
        totalQueries++;
        slots[goal.debugSlot()].finishedQuery();
    }
    
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Total goals:            ").append(totalGoals).append('\n');
        res.append("Recursive goals:        ").append(recursiveGoals).append('\n');
        res.append("Goal cache hits:        ").append(goalCacheHits).append('\n');
        res.append("Goal cache misses:      ").append(goalCacheMisses).append('\n');
        res.append("Total queries:          ").append(totalQueries).append('\n');
        for (GoalTypeStats stat : slots)
            res.append(stat);
        return res.toString();
    }
    
    public void starting(Goal goal) {
        slots[goal.debugSlot()].starting(goal);
    }
    
    public void startingEvaluation(Goal goal) {
        startTimes.put(goal, System.currentTimeMillis());
    }
    
    public void finishedEvaluation(Goal goal) {
        long startTime = startTimes.remove(goal);
        long endTime = System.currentTimeMillis();
        slots[goal.debugSlot()].addTime(endTime - startTime);
    }
    
}
