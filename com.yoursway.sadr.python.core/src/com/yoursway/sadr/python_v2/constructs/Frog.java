package com.yoursway.sadr.python_v2.constructs;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;

public class Frog {
    private final String accessor;
    private final boolean pattern;
    protected int id;
    
    /**
     * Accessor is list of characters; can end with "*" that means it's pattern
     * for completion
     */
    public Frog(String accessor) {
        pattern = accessor.endsWith("*");
        if (pattern) {
            this.accessor = accessor.substring(0, accessor.length() - 1);
        } else {
            this.accessor = accessor;
        }
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public int getLength() {
        return 1;
    }
    
    public boolean match(List<PythonConstruct> constructs) {
        PythonConstruct construct = constructs.get(getLength() - 1);
        return construct.match(this);
    }
    
    public boolean match(String name) {
        if (pattern) {
            return name.startsWith(accessor);
        } else {
            return name.equals(accessor);
        }
    }
    
    public String getAccessor() {
        return accessor;
    }
    
    public void filter(Map<String, RuntimeObject> attributes, PythonVariableAcceptor acceptor) {
        if (!pattern) {
            if (attributes.containsKey(accessor)) {
                acceptor.addResult(accessor, attributes.get(accessor));
            }
        }
        for (Entry<String, RuntimeObject> entry : attributes.entrySet()) {
            if (match(entry.getKey()))
                acceptor.addResult(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public String toString() {
        return this.accessor + (pattern ? "*" : "");
    }
    
    public boolean isPattern() {
        return pattern;
    }
}
