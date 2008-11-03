package com.yoursway.sadr.python_v2.croco;

import java.util.Map;
import java.util.Map.Entry;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;

public class Frog {
    public static final int SEARCH = -1;
    private final String accessor;
    private final boolean pattern;
    protected final int id;
    
    /**
     * Accessor is list of characters; can end with "*" that means it's pattern
     * for completion
     */
    public Frog(String accessor, int id) {
        this.id = id;
        pattern = accessor.endsWith("*");
        if (pattern) {
            this.accessor = accessor.substring(0, accessor.length() - 1);
        } else {
            this.accessor = accessor;
        }
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accessor == null) ? 0 : accessor.hashCode());
        result = prime * result + id;
        result = prime * result + (pattern ? 1231 : 1237);
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Frog other = (Frog) obj;
        if (accessor == null) {
            if (other.accessor != null)
                return false;
        } else if (!accessor.equals(other.accessor))
            return false;
        if (id != other.id)
            return false;
        if (pattern != other.pattern)
            return false;
        return true;
    }
    
    public int getId() {
        return id;
    }
    
    public int getLength() {
        return 1;
    }
    
    public boolean match(PythonConstruct element) {
        return element.match(this);
    }
    
    public boolean match(String name) {
        if (pattern) {
            return name.startsWith(accessor);
        } else {
            return name.equals(accessor);
        }
    }
    
    public String accessor() {
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
    
    protected String name() {
        return this.accessor + (pattern ? "*" : "");
    }
    
    @Override
    public String toString() {
        return name();
    }
    
    public boolean isPattern() {
        return pattern;
    }
}
