package com.yoursway.sadr.ruby.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RubyFile implements LocalVariableContainer {
    
    private final Collection<RubyLocalVariable> localVariables = new ArrayList<RubyLocalVariable>();
    private final Map<String, RubyLocalVariable> namesToLocalVariables = new HashMap<String, RubyLocalVariable>();
    
    private final String name;
    
    public RubyFile(RubyRuntimeModel model, String name) {
        this.name = name;
        model.addFile(this);
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
    
    public void addLocalVariable(RubyLocalVariable localVariable) {
        localVariables.add(localVariable);
        namesToLocalVariables.put(localVariable.name(), localVariable);
    }
    
    public RubyVariable findLocalVariable(String name) {
        return namesToLocalVariables.get(name);
    }
}
