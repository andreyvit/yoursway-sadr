/**
 * 
 */
package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.AssignmentInfoProvider;

public final class DelayedAssignmentsContinuation implements SimpleContinuation {
    private final AssignmentInfoProvider request;
    private final ValueInfoContinuation continuation;
    private final InfoKind infoKind;
    private final PythonDynamicContext dc;
    
    public DelayedAssignmentsContinuation(AssignmentInfoProvider request, PythonDynamicContext dc,
            InfoKind infoKind, ValueInfoContinuation continuation) {
        this.request = request;
        this.dc = dc;
        this.infoKind = infoKind;
        this.continuation = continuation;
    }
    
    public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
        return requestor.schedule((Continuation) new AssignmentsContinuation(null, request.assigned(), dc,
                infoKind, continuation));
    }
}