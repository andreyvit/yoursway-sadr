package com.yoursway.sadr.ruby.goals;

import com.yoursway.sadr.core.AbstractGoal;
import com.yoursway.sadr.core.PropagationStyle;

public class TypeG extends AbstractGoal {

    public TypeG() {
    }

    @Override
    public PropagationStyle propagationStyle() {
        return PropagationStyle.VALUE;
    }
    
}
