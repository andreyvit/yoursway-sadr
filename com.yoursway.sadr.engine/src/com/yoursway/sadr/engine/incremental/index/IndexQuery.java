package com.yoursway.sadr.engine.incremental.index;

import com.yoursway.sadr.engine.incremental.SourceUnit;

/**
 * @param <R>
 *            Requestor type
 */
public interface IndexQuery<R> {
    
    @Override
    public int hashCode();
    
    @Override
    public boolean equals(Object obj);
    
    SourceUnit localSourceUnit();
    
}
