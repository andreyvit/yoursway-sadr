package com.yoursway.sadr.engine.incremental.index;

import com.yoursway.sadr.engine.Goal;

public interface DependencyContributor {
    
    void contributeDependenciesTo(Goal<?> goal);
    
}
