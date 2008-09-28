package com.yoursway.sadr.engine.spi;

import static com.yoursway.utils.broadcaster.BroadcasterFactory.newBroadcaster;

import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.Result;
import com.yoursway.utils.broadcaster.Broadcaster;

public class CacheImpl implements GoalStateFactory {
    
    private final Map<Goal, Result> contextFreeCache = new HashMap<Goal, Result>();
    
    //    private final Map<Goal, ContextRelation> contextRelationsCache = new HashMap<Goal, ContextRelation>();
    //    private final Map<GoalContext, Result> contextSensitiveCache = new HashMap<GoalContext, Result>();
    
    private final GoalEngine goalEngine;
    
    private final Broadcaster<CacheListener> broadcaster = newBroadcaster(CacheListener.class);
    
    public CacheImpl(GoalEngine goalEngine) {
        if (goalEngine == null)
            throw new NullPointerException("goalEngine is null");
        this.goalEngine = goalEngine;
    }
    
    public GoalState createState(GoalState parentState, Goal goal) {
        Result cachedResult = contextFreeCache.get(goal);
        if (cachedResult != null) {
            broadcaster.fire().cacheHit(parentState, goal);
            goal.copyAnswerFrom(cachedResult);
            return null;
        }
        return goalEngine.createState(parentState, goal);
    }
    
}
