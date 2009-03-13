package com.yoursway.sadr.engine;

public class GoalTypeStats {
    
    public String name;
    
    public int total;
    
    public int hits;
    
    public int misses;
    
    public int queries;
    
    public long duration;
    
    public void finishedGoal(Goal<?> goal) {
        if (name == null)
            name = goal.getClass().getSimpleName();
        misses++;
        total++;
    }
    
    public void finishedRun(Goal<?> goal, long goalDuration) {
        queries += 1;
        duration += goalDuration;
    }
    
    public void cacheHit() {
        total++;
        hits++;
    }
    
    @Override
    public String toString() {
        if (name == null)
            return "";
        StringBuilder res = new StringBuilder();
        res.append('\n');
        res.append(name).append("\n");
        res.append("Total goals:            ").append(total).append('\n');
        res.append("Goal cache hits:        ").append(hits).append('\n');
        res.append("Goal cache misses:      ").append(misses).append('\n');
        res.append("Total queries:          ").append(queries).append('\n');
        res.append("TIME:                   ").append(duration).append('\n');
        res.append("TIME per cache miss:    ").append((duration * 100 / misses) / 100.0).append('\n');
        return res.toString();
    }
    
}
