package com.yoursway.sadr.python.model;

import com.yoursway.sadr.python.index.unodes.Unode;

public abstract class IndexNameWrappingStrategy {
    
    public abstract Unode wrap(Unode unode);
    
    public static final IndexNameWrappingStrategy NULL = new IndexNameWrappingStrategy() {
        
        @Override
        public Unode wrap(Unode unode) {
            return unode;
        }
    };
    
}
