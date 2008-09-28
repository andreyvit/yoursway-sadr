package com.yoursway.sadr.engine.spi;

import com.yoursway.sadr.engine.Goal;

public interface CacheListener {
    
    void cacheHit(GoalState parentState, Goal goal);
    
}
