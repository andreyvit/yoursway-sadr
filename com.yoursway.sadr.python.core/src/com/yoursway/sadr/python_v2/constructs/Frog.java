package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.goals.sideeffects.TransferOfControl;

public abstract class Frog {
    
    public abstract Frog replace(Frog lhs, Frog rhs);
    
    public RuntimeObject compactValue() {
        return null;
    }
    
    public TransferOfControl compactSideEffect() {
        return null;
    }
    
}
