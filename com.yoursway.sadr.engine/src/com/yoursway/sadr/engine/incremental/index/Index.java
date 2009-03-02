package com.yoursway.sadr.engine.incremental.index;

import kilim.pausable;

import com.yoursway.sadr.engine.incremental.SourceUnit;

public interface Index {
    
    @pausable
    <R> void query(IndexQuery<R> query, R requestor);
    
    IndexMemento createMemento(SourceUnit sourceUnit);
    
}
