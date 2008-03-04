package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.CallC;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.Wildcard;

public class CallInfo {
    
    private final Wildcard wildcard;
    private final CallC construct;
    
    public CallInfo(Wildcard wildcard, CallC construct) {
        this.wildcard = wildcard;
        this.construct = construct;
    }
    
    public Wildcard getWildcard() {
        return wildcard;
    }
    
    public CallC construct() {
        return construct;
    }
    
}
