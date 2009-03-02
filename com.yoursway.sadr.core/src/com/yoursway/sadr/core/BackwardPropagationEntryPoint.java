package com.yoursway.sadr.core;

import kilim.pausable;

import com.yoursway.sadr.engine.Goal;

public interface BackwardPropagationEntryPoint {
    
    boolean canDoBackwardPropagation(Goal<?> goal);
    
    @pausable
    IValueInfo backwardPropagation(Goal<?> goal);
    
}
