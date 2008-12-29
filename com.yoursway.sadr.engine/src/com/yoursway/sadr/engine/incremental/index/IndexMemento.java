package com.yoursway.sadr.engine.incremental.index;

import java.util.Collection;

public interface IndexMemento {
    
    Collection<IndexQuery<?>> diff(IndexMemento previous);
    
}
