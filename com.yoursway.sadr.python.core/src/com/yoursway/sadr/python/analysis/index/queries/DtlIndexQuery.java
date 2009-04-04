package com.yoursway.sadr.python.analysis.index.queries;

import com.yoursway.sadr.engine.incremental.index.IndexQuery;

public interface DtlIndexQuery<R> extends IndexQuery<R> {
    
    void accept(DtlIndexQueryVisitor visitor, R requestor);
    
}
