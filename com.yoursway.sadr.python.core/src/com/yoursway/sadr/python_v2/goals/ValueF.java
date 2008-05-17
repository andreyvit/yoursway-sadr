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
    
    public RuntimeObject getValue() {
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
    
}
