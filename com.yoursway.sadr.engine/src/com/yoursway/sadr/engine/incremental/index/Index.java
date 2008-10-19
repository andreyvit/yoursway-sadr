package com.yoursway.sadr.engine.incremental.index;

import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.incremental.SourceUnit;

public interface Index {
    
    <R> void query(IndexQuery<R> query, ContinuationScheduler cs, R requestor);
    
    IndexMemento createMemento(SourceUnit sourceUnit);
    
}
