package com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests;

public interface VariableAffector {
    
    void actOnVariable(AssignmentInfoRequestor subject);
    
}
