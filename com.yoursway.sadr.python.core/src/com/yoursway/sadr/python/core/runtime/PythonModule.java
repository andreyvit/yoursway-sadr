package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PythonModule implements LocalVariableContainer {
    
    private final Collection<PythonLocalVariable> localVariables = new ArrayList<PythonLocalVariable>();
    private final Map<String, PythonLocalVariable> namesToLocalVariables = new HashMap<String, PythonLocalVariable>();
    
    private final String name;
    
    public PythonModule(PythonRuntimeModel model, String name) {
        this.name = name;
        model.addModule(this);
    }
    
    public String name() {
        return name;
    }
    
    public boolean matches(String prefix) {
        return name.startsWith(prefix);
    }
    
    public String qualifiedName() {
        return name;
    }
    
    public void addLocalVariable(PythonLocalVariable localVariable) {
        localVariables.add(localVariable);
        namesToLocalVariables.put(localVariable.name().toLowerCase(), localVariable);
    }
    
    public PythonVariable findLocalVariable(String name) {
        return namesToLocalVariables.get(name.toLowerCase());
    }
}
