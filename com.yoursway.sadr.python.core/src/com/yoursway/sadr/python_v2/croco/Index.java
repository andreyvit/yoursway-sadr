package com.yoursway.sadr.python_v2.croco;

import java.util.HashMap;

import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

public class Index {
    
    HashMap<Krocodile, HashMap<PythonConstruct, PythonRecord>> simple = new HashMap<Krocodile, HashMap<PythonConstruct, PythonRecord>>();
    HashMap<Frog, PythonRecord> advanced = new HashMap<Frog, PythonRecord>();
    
    public PythonRecord lookup(Krocodile k, PythonConstruct c) {
        if (c == null)
            throw new IllegalArgumentException("c is null");
        if (k == null)
            throw new IllegalArgumentException("k is null");
        HashMap<PythonConstruct, PythonRecord> scope = simple.get(k);
        if (scope == null)
            return null;
        return scope.get(c);
    }
    
    public PythonRecord lookup(Frog f) {
        if (f == null)
            throw new IllegalArgumentException("f is null");
        return advanced.get(f);
    }
    
    public void add(Krocodile k, PythonConstruct c, PythonRecord r) {
        if (r == null)
            throw new IllegalArgumentException("r is null");
        if (c == null)
            throw new IllegalArgumentException("c is null");
        if (k == null)
            throw new IllegalArgumentException("k is null");
        HashMap<PythonConstruct, PythonRecord> scope = simple.get(k);
        if (scope == null) {
            scope = new HashMap<PythonConstruct, PythonRecord>();
            simple.put(k, scope);
        }
        scope.put(c, r);
    }
    
    public void add(Frog f, PythonRecord r) {
        if (f == null)
            throw new IllegalArgumentException("f is null");
        if (r == null)
            throw new IllegalArgumentException("r is null");
        advanced.put(f, r);
    }
}
