package com.yoursway.sadr.engine;

import java.util.Collection;
import java.util.List;

import kilim.pausable;

public interface AnalysisTask {
    
    @pausable
    <R extends Result> R evaluate(Goal<R> goal);
    
    @pausable
    <R extends Result> List<R> evaluate(Collection<? extends Goal<R>> goals);
    
}
