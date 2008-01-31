package com.yoursway.sadr.core;

import java.util.LinkedList;
import java.util.Queue;

public class QueryQueue implements QueryEnqueuer {
    
    private static Queue<Query> queue = new LinkedList<Query>();
    
    public void enqueue(Query query) {
        queue.add(query);
    }
    
    public Query poll() {
        return queue.poll();
    }
    
}
