package com.yoursway.sadr.python.analysis.index;

import com.yoursway.sadr.python.analysis.index.wrapping.IndexNameWrappingStrategy;

public interface IndexAffector {
    
    void actOnIndex(IndexRequest indexRequest);
    
    IndexNameWrappingStrategy createWrappingStrategy();
    
}
