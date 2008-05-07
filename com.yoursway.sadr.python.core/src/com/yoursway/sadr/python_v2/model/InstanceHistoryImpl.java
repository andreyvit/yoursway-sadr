package com.yoursway.sadr.python_v2.model;

import java.util.HashSet;
import java.util.Set;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

public class InstanceHistoryImpl implements InstanceHistory {
    private final Set<RuntimeObject> history = new HashSet<RuntimeObject>();
    private final PythonConstruct decl;
    private final RuntimeObject initialState;
    
    public InstanceHistoryImpl(RuntimeObject source, PythonConstruct decl) {
        initialState = source;
        this.decl = decl;
        history.add(source);
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
