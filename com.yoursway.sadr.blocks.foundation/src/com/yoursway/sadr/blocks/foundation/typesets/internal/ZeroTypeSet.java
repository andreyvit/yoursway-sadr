package com.yoursway.sadr.blocks.foundation.typesets.internal;

import java.util.Collection;
import java.util.Collections;

import com.yoursway.sadr.blocks.foundation.types.Type;

public class ZeroTypeSet extends AbstractTypeSet {
    
    public ZeroTypeSet() {
    }
    
    @Override
    public String toString() {
        return "()";
    }
    
    public Collection<Type> containedTypes() {
        return Collections.emptyList();
    }
    
    public boolean isEmpty() {
        return true;
    }
    
}
