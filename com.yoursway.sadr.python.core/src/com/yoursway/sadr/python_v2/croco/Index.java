package com.yoursway.sadr.python_v2.croco;

import java.util.HashMap;
import java.util.HashSet;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

public class Index {
    
    private static HashMap<Krocodile, HashMap<PythonConstruct, PythonRecord>> simple = new HashMap<Krocodile, HashMap<PythonConstruct, PythonRecord>>();
    private static HashMap<Frog, PythonRecord> advanced = new HashMap<Frog, PythonRecord>();
    private static HashSet<Scope> scopes = new HashSet<Scope>();
    
    private static int counter = 0;
    
    public static int newId() {
        return ++counter;
    }
    
    public static PythonRecord lookup(Krocodile k, PythonConstruct c) {
        if (c == null)
            throw new IllegalArgumentException("c is null");
        if (k == null)
            throw new IllegalArgumentException("k is null");
        HashMap<PythonConstruct, PythonRecord> scope = simple.get(k);
        if (scope == null)
            return null;
        return scope.get(c);
    }
    
    public static PythonRecord lookup(Frog f) {
        if (f == null)
            throw new IllegalArgumentException("f is null");
        return advanced.get(f);
    }
    
    public static void add(Krocodile k, PythonConstruct c, PythonRecord r) {
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
    
    public static void add(Frog f, PythonRecord r) {
        if (f == null)
            throw new IllegalArgumentException("f is null");
        if (r == null)
            throw new IllegalArgumentException("r is null");
        advanced.put(f, r);
    }
    
    public static PythonRecord newRecord(String name) {
        PythonRecord record = new PythonRecord(Index.newId());
        Frog frog = Frog.newFrog(name);
        record.addFrog(frog);
        Index.add(frog, record);
        return record;
    }
    
    public static PythonRecord newRecord(String name, Krocodile crocodile, PythonConstruct construct) {
        scopes.add(construct.parentScope());
        System.out.println("[i] Adding '" + name + "' from " + construct);
        PythonRecord record = Index.newRecord(name);
        Index.add(crocodile, construct, record);
        return record;
    }
    
    public static boolean isIndexed(Scope scope) {
        return scopes.contains(scope);
    }
}
