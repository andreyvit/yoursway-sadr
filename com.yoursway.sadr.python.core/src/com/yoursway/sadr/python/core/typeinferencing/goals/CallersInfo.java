package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.engine.Result;
import com.yoursway.sadr.python_v2.constructs.CallC;

public class CallersInfo implements Result {
    
    private final CallC[] callers;
    
    public CallersInfo(CallC[] callers) {
        this.callers = callers;
    }
    
    public CallC[] callers() {
        return callers;
    }
    
}
