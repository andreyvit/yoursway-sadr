package com.yoursway.sadr.engine.v1;

import java.util.LinkedList;
import java.util.Queue;

public class QueryQueue implements QueryEnqueuer {
    
    private final Queue<Query> queue = new LinkedList<Query>();
    
    public void enqueue(Query query) {
        queue.add(query);
    }
    
    public Query poll() {
        return queue.poll();
    }
    
}
