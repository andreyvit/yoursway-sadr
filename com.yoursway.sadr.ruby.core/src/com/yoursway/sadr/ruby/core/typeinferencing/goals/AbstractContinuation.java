package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.SimpleContinuation;

public abstract class AbstractContinuation implements Continuation, SimpleContinuation {
    
    public final void run(ContinuationRequestor requestor) {
        requestor.subgoal(this);
    }
    
}
