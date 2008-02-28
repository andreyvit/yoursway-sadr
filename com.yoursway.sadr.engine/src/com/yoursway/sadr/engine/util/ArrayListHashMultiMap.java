package com.yoursway.sadr.engine.util;

import java.util.ArrayList;
import java.util.Collection;

public class ArrayListHashMultiMap<K, V> extends HashMultiMap<K, V> {
    
    @Override
    protected Collection<V> createInnerCollection() {
        return new ArrayList<V>();
    }
    
}
