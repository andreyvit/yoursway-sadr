package com.yoursway.sadr.engine.util;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;

public abstract class IdentityHashMultiMap<K, V> extends AbstractMultiMap<K, V> {
    
    @Override
    protected Map<K, Collection<V>> createMap() {
        return new IdentityHashMap<K, Collection<V>>();
    }
    
}
