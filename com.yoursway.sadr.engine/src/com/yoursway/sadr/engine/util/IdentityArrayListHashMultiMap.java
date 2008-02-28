package com.yoursway.sadr.engine.util;

import java.util.ArrayList;
import java.util.Collection;

public class IdentityArrayListHashMultiMap<K, V> extends IdentityHashMultiMap<K, V> {
    
    @Override
    protected Collection<V> createInnerCollection() {
        return new ArrayList<V>();
    }
    
}
