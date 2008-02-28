package com.yoursway.sadr.engine.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class HashMultiMap<K, V> extends AbstractMultiMap<K, V> {
    
    @Override
    protected Map<K, Collection<V>> createMap() {
        return new HashMap<K, Collection<V>>();
    }
    
}
