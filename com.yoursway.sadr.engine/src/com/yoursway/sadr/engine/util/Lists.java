package com.yoursway.sadr.engine.util;

import static com.google.common.collect.Lists.newArrayListWithCapacity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;

public class Lists {
    
    public static <T> List<T> combineLists(Collection<? extends T> a, Collection<? extends T> b) {
        List<T> allNodes = new ArrayList<T>(a.size() + b.size());
        allNodes.addAll(a);
        allNodes.addAll(b);
        return allNodes;
    }
    
    public static <T> Set<T> combineSets(Collection<? extends T> a, Collection<? extends T> b) {
        Set<T> allNodes = new HashSet<T>(a.size() + b.size());
        allNodes.addAll(a);
        allNodes.addAll(b);
        return allNodes;
    }
    
    public static <T> List<T> filterInto(Collection<? extends T> source, List<T> storage,
            Predicate<? super T> pred) {
        Iterators.addAll(storage, Iterators.filter(source.iterator(), pred));
        return storage;
    }
    
    public static <T> Collection<T> filterInto(Collection<? extends T> source, Collection<T> storage,
            Predicate<? super T> pred) {
        Iterators.addAll(storage, Iterators.filter(source.iterator(), pred));
        return storage;
    }
    
    public static <T> List<T> filter(Collection<? extends T> source, Predicate<? super T> pred) {
        List<T> storage = newArrayListWithCapacity(source.size());
        return filterInto(source, storage, pred);
    }
    
}
