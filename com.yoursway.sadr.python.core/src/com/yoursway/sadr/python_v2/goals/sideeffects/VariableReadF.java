package com.yoursway.sadr.python_v2.goals.sideeffects;

import com.yoursway.sadr.python_v2.constructs.Frog;

public class VariableReadF extends Frog {
    
    private final String name;
    
    public VariableReadF(String name) {
        if (name == null)
            throw new NullPointerException("name is null");
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "[" + name + "]";
    }
    
    @Override
    public Frog replace(Frog lhs, Frog rhs) {
        if (lhs.equals(this))
            return rhs;
        return this;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        final VariableReadF other = (VariableReadF) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
}
