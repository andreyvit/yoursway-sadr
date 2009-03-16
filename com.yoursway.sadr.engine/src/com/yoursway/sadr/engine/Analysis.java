package com.yoursway.sadr.engine;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kilim.pausable;

import com.google.common.base.Function;

public class Analysis {
    
    @pausable
    public static <R extends Result, X> List<R> evaluate(Collection<? extends X> data,
            Function<? super X, ? extends Goal<R>> map) {
        List<Goal<R>> goals = new ArrayList<Goal<R>>(data.size());
        for (X item : data) {
            Goal<R> goal = map.apply(item);
            if (goal == null)
                throw new NullPointerException("Mapping function returned a null goal in reply to " + item);
            goals.add(goal);
        }
        return AnalysisEngine.currentTask().evaluate(goals);
    }
    
    @pausable
    public static <R extends Result> List<R> evaluate(Collection<? extends Goal<R>> goals) {
        return AnalysisEngine.currentTask().evaluate(goals);
    }
    
    @pausable
    public static <R extends Result> List<R> evaluate(Goal<R>... goals) {
        return AnalysisEngine.currentTask().evaluate(newArrayList(goals));
    }
    
    @pausable
    public static <R extends Result> R evaluate(Goal<R> goal) {
        return AnalysisEngine.currentTask().evaluate(goal);
    }
    
    @pausable
    public static <R extends Result> Pair<R, R> evaluate(Goal<R> a, Goal<R> b) {
        List<Goal<R>> goals = new ArrayList<Goal<R>>(2);
        goals.add(a);
        goals.add(b);
        List<? extends R> results = evaluate(goals);
        return new Pair<R, R>(results.get(0), results.get(1));
    }
    
}
