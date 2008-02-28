package com.yoursway.sadr.engine;

import org.eclipse.core.runtime.Platform;

public class GoalDebug {
    
    public static final boolean TRACE_GOALS = "true".equalsIgnoreCase(Platform
            .getDebugOption("com.esko.dtl.core/traceGoals"));
    
    private static final int SHIFT_WIDTH = 2;
    
    private int indent = 0;
    
    public void starting(Goal goal, Goal parent) {
        if (TRACE_GOALS)
            System.out.println(indent() + "BEGIN " + goal);
        ++indent;
    }
    
    public void recursive(Goal goal, Goal parent) {
        if (TRACE_GOALS)
            System.out.println(indent() + "RECUR " + goal);
    }
    
    public void finished(Goal goal, Goal parent) {
        --indent;
        if (TRACE_GOALS)
            System.out.println(indent() + "FIN   " + goal);
    }
    
    private String indent() {
        int spaces = indent * SHIFT_WIDTH;
        StringBuilder res = new StringBuilder(spaces);
        for (int i = 0; i < spaces; i++)
            res.append(' ');
        return res.toString();
    }
    
}
