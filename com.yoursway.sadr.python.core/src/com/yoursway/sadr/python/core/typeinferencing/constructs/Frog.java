package com.yoursway.sadr.python.core.typeinferencing.constructs;

import com.yoursway.sadr.python_v2.model.RuntimeObject;

public abstract class Frog {
    
    public abstract Frog replace(Frog lhs, Frog rhs);
    
    public RuntimeObject compact() {
        return null;
    }
    
}
