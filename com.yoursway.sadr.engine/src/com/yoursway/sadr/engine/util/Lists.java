package com.yoursway.sadr.engine.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    
}
