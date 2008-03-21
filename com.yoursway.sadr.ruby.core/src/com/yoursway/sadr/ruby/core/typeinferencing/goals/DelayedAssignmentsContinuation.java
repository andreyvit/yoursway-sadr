/**
 * 
 */
package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl.rq.VariableRequest;

public final class DelayedAssignmentsContinuation implements SimpleContinuation {
    private final VariableRequest request;
    private final ValueInfoContinuation continuation;
    private final InfoKind infoKind;
    
    public DelayedAssignmentsContinuation(VariableRequest request, InfoKind infoKind,
            ValueInfoContinuation continuation) {
        this.request = request;
        this.infoKind = infoKind;
        this.continuation = continuation;
    }
    
    public void run(ContinuationRequestor requestor) {
        requestor.subgoal(new AssignmentsContinuation(null, request.assigned(), infoKind, continuation));
    }
}