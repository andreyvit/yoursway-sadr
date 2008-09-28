package com.yoursway.sadr.engine;

import java.util.LinkedList;
import java.util.Queue;

import com.yoursway.sadr.engine.spi.AnalysisComputer;

public class QueryQueue implements AnalysisComputer {
    
    private final Queue<Query> queue = new LinkedList<Query>();
    
    public Query poll() {
        return queue.poll();
    }
    
    public void enqueueComputation(Query query) {
        queue.add(query);
    }
    
}
