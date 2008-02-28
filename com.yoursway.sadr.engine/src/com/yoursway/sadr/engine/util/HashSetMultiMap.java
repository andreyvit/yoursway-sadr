package com.yoursway.sadr.engine.util;

import java.util.Collection;
import java.util.HashSet;

public class HashSetMultiMap<K,V> extends HashMultiMap<K, V> {
    
    @Override
    protected Collection<V> createInnerCollection() {
        return new HashSet<V>();
    }
    
}
