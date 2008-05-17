package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.Frog;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class ValueF extends Frog {
    
    private final RuntimeObject value;
    
    public ValueF(RuntimeObject value) {
        if (value == null)
            throw new NullPointerException("value is null");
        this.value = value;
    }
    
    @Override
    public RuntimeObject compactValue() {
        return value;
    }
    
    @Override
    public Frog replace(Frog lhs, Frog rhs) {
        if (lhs.equals(this))
            return rhs;
        return this;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + value + ")";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        final ValueF other = (ValueF) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
    
}
