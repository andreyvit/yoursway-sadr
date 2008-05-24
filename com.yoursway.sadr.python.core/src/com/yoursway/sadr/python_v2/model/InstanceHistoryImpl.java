package com.yoursway.sadr.python_v2.model;

import java.util.HashSet;
import java.util.Set;

import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

public class InstanceHistoryImpl implements InstanceHistory {
    private final Set<RuntimeObject> history = new HashSet<RuntimeObject>();
    private final RuntimeObject initialState;
    private final PythonConstruct decl;
    
    public InstanceHistoryImpl(RuntimeObject source, PythonConstruct decl) {
        if (source == null) {
            throw new IllegalArgumentException("Source can't be null");
        }
        initialState = source;
        history.add(source);
        this.decl = decl;
    }
    
    void addInstance(RuntimeObject instance) {
        history.add(instance);
    }
    
    public boolean contains(RuntimeObject instance) {
        return history.contains(instance);
    }
    
    public RuntimeObject source() {
        return initialState;
    }
    
    public PythonConstruct sourceDeclaration() {
        return decl;
    }
}
