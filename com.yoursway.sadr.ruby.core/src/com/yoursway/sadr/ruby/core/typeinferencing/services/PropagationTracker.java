package com.yoursway.sadr.ruby.core.typeinferencing.services;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.IConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.Request;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.ValueInfoContinuation;

public interface PropagationTracker {
    
    void initiateBackwardPropagation(Goal goal, ContinuationRequestor requestor,
            ValueInfoContinuation continuation);
    
    void traverseEntirely(IConstruct construct, Request request, ContinuationRequestor requestor,
            SimpleContinuation continuation);
    
}
