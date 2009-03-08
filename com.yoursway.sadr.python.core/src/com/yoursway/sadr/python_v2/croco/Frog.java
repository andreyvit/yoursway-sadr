package com.yoursway.sadr.python_v2.croco;

import java.util.Map;
import java.util.Map.Entry;

import com.yoursway.sadr.python_v2.constructs.PythonDeclaration;
import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

public class Frog {
    public static final int SEARCH = -1;
    public static final int UNKNOWN = 0;
    private final String accessor;
    private final boolean pattern;
    protected int id;
    
    /**
     * Accessor is list of characters; can end with "*" that means it's pattern
     * for completion
     */
    protected Frog(String accessor, int id) {
        if (id != SEARCH && id != UNKNOWN)
            throw new IllegalArgumentException("you may assign frog only with setId() method!");
        this.id = id;
        pattern = accessor.endsWith("*");
        if (pattern) {
            this.accessor = accessor.substring(0, accessor.length() - 1);
        } else {
            this.accessor = accessor;
        }
    }
    
    public static Frog searchFrog(String name) {
        return new Frog(name, SEARCH);
    }
    
    public static Frog newFrog(String name) {
        return new Frog(name, UNKNOWN);
    }
    
    public void setId(int id) {
        this.id = id;
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
    
    public boolean match(PythonDeclaration element) {
        return element.match(this);
    }
    
    public boolean match(String name) {
        if (name == null)
            return false;
        if (pattern) {
            return name.startsWith(accessor);
        } else {
            return name.equals(accessor);
        }
    }
    
    public String accessor() {
        return accessor;
    }
    
    public void filter(Map<String, PythonObject> attributes, PythonVariableAcceptor acceptor) {
        if (!pattern) {
            if (attributes.containsKey(accessor)) {
                acceptor.addResult(accessor, attributes.get(accessor));
            }
        }
        for (Entry<String, PythonObject> entry : attributes.entrySet()) {
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
