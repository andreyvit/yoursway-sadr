package com.yoursway.sadr.engine.spi;

import com.yoursway.sadr.engine.Query;

public interface AnalysisComputer {
    
    void enqueueComputation(Query query);
    
}
