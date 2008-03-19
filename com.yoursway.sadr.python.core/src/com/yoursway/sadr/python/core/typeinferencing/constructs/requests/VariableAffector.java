package com.yoursway.sadr.python.core.typeinferencing.constructs.requests;

public interface VariableAffector {
    
    void actOnVariable(AssignmentInfoRequestor subject);
    
}
