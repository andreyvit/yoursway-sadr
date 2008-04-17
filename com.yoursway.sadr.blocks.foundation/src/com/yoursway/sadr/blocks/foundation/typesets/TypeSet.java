package com.yoursway.sadr.blocks.foundation.typesets;

import java.util.Collection;

import com.yoursway.sadr.blocks.foundation.types.Type;

public interface TypeSet {
    
    boolean isEmpty();
    
    Collection<Type> containedTypes();
    
    String[] describePossibleTypes();
    
}
