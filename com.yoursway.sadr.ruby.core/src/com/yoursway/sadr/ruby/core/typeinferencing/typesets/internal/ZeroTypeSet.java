package com.yoursway.sadr.ruby.core.typeinferencing.typesets.internal;

import java.util.Collection;
import java.util.Collections;

import com.yoursway.sadr.ruby.core.typeinferencing.types.Type;

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
