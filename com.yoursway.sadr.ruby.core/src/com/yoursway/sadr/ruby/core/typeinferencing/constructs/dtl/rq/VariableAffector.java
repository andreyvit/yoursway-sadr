package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl.rq;

import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.AssignmentInfoRequestor;

public interface VariableAffector {
    
    void actOnVariable(AssignmentInfoRequestor subject);
    
}
