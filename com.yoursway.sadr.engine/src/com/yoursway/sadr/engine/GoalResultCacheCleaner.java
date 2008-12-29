package com.yoursway.sadr.engine;

import java.util.Collection;

public interface GoalResultCacheCleaner {
    
    void removeCachedResultsOf(Collection<Goal<?>> goals);
    
}
