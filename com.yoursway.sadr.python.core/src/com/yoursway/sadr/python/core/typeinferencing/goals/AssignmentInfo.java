package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.Wildcard;

public class AssignmentInfo {
    
    private final Wildcard wildcard;
    private final PythonConstruct construct;
    
    public AssignmentInfo(Wildcard wildcard, PythonConstruct construct) {
        this.wildcard = wildcard;
        this.construct = construct;
    }
    
    public Wildcard wildcard() {
        return wildcard;
    }
    
    public PythonConstruct construct() {
        return construct;
    }
    
}
