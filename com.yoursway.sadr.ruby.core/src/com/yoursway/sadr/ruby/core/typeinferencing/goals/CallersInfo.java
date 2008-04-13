package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import com.yoursway.sadr.engine.Result;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.CallC;

public class CallersInfo implements Result {
    
    private final CallC[] callers;
    
    public CallersInfo(CallC[] callers) {
        this.callers = callers;
    }
    
    public CallC[] callers() {
        return callers;
    }
    
}
