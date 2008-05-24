package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.blocks.foundation.wildcards.Wildcard;
import com.yoursway.sadr.python_v2.constructs.CallC;

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
