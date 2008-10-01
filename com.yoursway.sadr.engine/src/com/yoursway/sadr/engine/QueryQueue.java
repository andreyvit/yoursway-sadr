package com.yoursway.sadr.engine;

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
    
    public void clear() {
        queue.clear();
    }
    
}
