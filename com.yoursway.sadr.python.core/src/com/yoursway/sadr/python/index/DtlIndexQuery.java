package com.yoursway.sadr.python.index;

import com.yoursway.sadr.engine.incremental.index.IndexQuery;

public interface DtlIndexQuery<R> extends IndexQuery<R> {
    
    void accept(DtlIndexQueryVisitor visitor, R requestor);
    
}
