package com.yoursway.sadr.python.core.typeinferencing.constructs;

import com.yoursway.sadr.python_v2.goals.TransferOfControl;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public abstract class Frog {
    
    public abstract Frog replace(Frog lhs, Frog rhs);
    
    public RuntimeObject compactValue() {
        return null;
    }
    
    public TransferOfControl compactSideEffect() {
        return null;
    }
    
}
