package com.yoursway.sadr.ruby.goals;

import com.yoursway.sadr.core.AbstractGoal;
import com.yoursway.sadr.core.PropagationStyle;

public class WorldG extends AbstractGoal {

    public WorldG() {
    }

    @Override
    public PropagationStyle propagationStyle() {
        return PropagationStyle.FLOW;
    }
    
    
}
