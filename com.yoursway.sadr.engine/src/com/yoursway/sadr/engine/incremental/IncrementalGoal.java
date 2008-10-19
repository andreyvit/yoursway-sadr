package com.yoursway.sadr.engine.incremental;

import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.Result;

public interface IncrementalGoal<R extends Result> extends Goal<R> {
    
    SourceUnit getInherentSourceUnitDependency();
    
}
