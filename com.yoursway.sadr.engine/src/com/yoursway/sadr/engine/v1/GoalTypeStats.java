package com.yoursway.sadr.engine.v1;

public class GoalTypeStats {
    
    public String name;
    
    public int total;
    
    public int hits;
    
    public int misses;
    
    public int queries;
    
    public long duration;
    
    public void starting(Goal goal) {
        if (name == null)
            name = goal.getClass().getSimpleName();
    }
    
    public void finishedQuery() {
        queries++;
    }
    
    public void calculatedGoal() {
        misses++;
        total++;
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
    
    public void addTime(long l) {
        duration += l;
    }
    
}