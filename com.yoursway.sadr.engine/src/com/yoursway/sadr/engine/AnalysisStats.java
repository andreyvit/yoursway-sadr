package com.yoursway.sadr.engine;

public class AnalysisStats {
    
    private int totalGoals = 0, recursiveGoals = 0;
    
    final int totalQueries = 0;
    
    private int goalCacheHits = 0, goalCacheMisses = 0;
    
    private long totalDuration = 0;
    
    private int queueRuns = 0, rootGoals = 0;
    
    private long queueRunsDuration = 0, analysisDuration = 0;
    
    private final GoalTypeStats[] slots = new GoalTypeStats[8];
    
    public AnalysisStats() {
        for (int i = 0; i < slots.length; i++)
            slots[i] = new GoalTypeStats();
    }
    
    public void recursiveGoal(Goal<?> goal) {
        totalGoals++;
        recursiveGoals++;
    }
    
    public void cacheHit(Goal<?> goal) {
        totalGoals++;
        goalCacheHits++;
        slots[goal.debugSlot()].cacheHit();
    }
    
    public void finishedGoal(Goal<?> goal) {
        totalGoals++;
        goalCacheMisses++;
        slots[goal.debugSlot()].finishedGoal(goal);
    }
    
    public void finishedRun(Goal<?> goal, long goalDuration) {
        totalGoals++;
        goalCacheMisses++;
        totalDuration += goalDuration;
        slots[goal.debugSlot()].finishedRun(goal, goalDuration);
    }
    
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Time spent in analysis: ").append(analysisDuration).append(" ms\n");
        res.append("Time spent running root goals: ").append(queueRunsDuration).append(" ms\n");
        res.append("Time spent in goals:    ").append(totalDuration).append(" ms\n");
        res.append("Root goals:             ").append(rootGoals).append("\n");
        res.append("Root goals evaluated:   ").append(queueRuns).append("\n");
        res.append("Total goals:            ").append(totalGoals).append('\n');
        res.append("\n");
        res.append("Recursive goals:        ").append(recursiveGoals).append('\n');
        res.append("Goal cache hits:        ").append(goalCacheHits).append('\n');
        res.append("Goal cache misses:      ").append(goalCacheMisses).append('\n');
        res.append("Total queries:          ").append(totalQueries).append('\n');
        for (GoalTypeStats stat : slots)
            res.append(stat);
        return res.toString();
    }
    
    public void queueRunDone(long duration) {
        queueRuns++;
        queueRunsDuration += duration;
    }
    
    public void rootGoalDone(long duration) {
        rootGoals++;
        analysisDuration += duration;
    }
    
}
