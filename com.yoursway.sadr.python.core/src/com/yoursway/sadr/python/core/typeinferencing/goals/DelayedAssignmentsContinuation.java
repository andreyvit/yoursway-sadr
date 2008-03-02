/**
 * 
 */
package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.VariableRequest;

public final class DelayedAssignmentsContinuation implements SimpleContinuation {
    private final VariableRequest request;
    private final ValueInfoContinuation continuation;
    private final InfoKind infoKind;
    private final PythonDynamicContext dc;
    
    public DelayedAssignmentsContinuation(VariableRequest request, PythonDynamicContext dc,
            InfoKind infoKind, ValueInfoContinuation continuation) {
        this.request = request;
        this.dc = dc;
        this.infoKind = infoKind;
        this.continuation = continuation;
    }
    
    public void run(ContinuationRequestor requestor) {
        requestor.subgoal(new AssignmentsContinuation(null, request.assigned(), dc, infoKind, continuation));
    }
}