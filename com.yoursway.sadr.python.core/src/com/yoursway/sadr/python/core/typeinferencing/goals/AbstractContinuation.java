package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.SimpleContinuation;

public abstract class AbstractContinuation implements Continuation, SimpleContinuation {
    
    public final ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
        return requestor.schedule((Continuation) this);
    }
    
}
