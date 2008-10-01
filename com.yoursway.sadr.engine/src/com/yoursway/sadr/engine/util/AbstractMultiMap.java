package com.yoursway.sadr.engine.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public abstract class AbstractMultiMap<K, V> {
    
    private final Map<K, Collection<V>> data = createMap();
    
    protected abstract Map<K, Collection<V>> createMap();
    
    public AbstractMultiMap() {
        super();
    }
    
    public void put(K key, V value) {
        lookupCollection(key).add(value);
    }
    
    protected Collection<V> lookupCollection(K key) {
        Collection<V> coll = data.get(key);
        if (coll == null) {
            coll = createInnerCollection();
            data.put(key, coll);
        }
        return coll;
    }
    
    public Collection<V> get(K key) {
        Collection<V> coll = data.get(key);
        if (coll == null)
            return Collections.emptySet();
        else
            return coll;
    }
    
    protected abstract Collection<V> createInnerCollection();
    
    public void clear() {
        data.clear();
    }
    
    public boolean containsKey(K key) {
        return data.containsKey(key);
    }
    
    public boolean containsValue(V value) {
        for (Collection<V> values : data.values())
            if (values.contains(value))
                return true;
        return false;
    }
    
    @Override
    public boolean equals(Object o) {
        return data.equals(o);
    }
    
    @Override
    public int hashCode() {
        return data.hashCode();
    }
    
    public boolean isEmpty() {
        return data.isEmpty();
    }
    
    public Set<K> keySet() {
        return data.keySet();
    }
    
    public Collection<V> remove(K key) {
        Collection<V> coll = data.remove(key);
        if (coll == null)
            return Collections.emptySet();
        else
            return coll;
    }
    
    public int valuesCount() {
        int size = 0;
        for (Collection<V> values : data.values())
            size += values.size();
        return size;
    }
    
    public int keysCount() {
        return data.size();
    }
    
    public Collection<Collection<V>> values() {
        return data.values();
    }
    
    public Set<Entry<K, Collection<V>>> entrySet() {
        return data.entrySet();
    }
    
}