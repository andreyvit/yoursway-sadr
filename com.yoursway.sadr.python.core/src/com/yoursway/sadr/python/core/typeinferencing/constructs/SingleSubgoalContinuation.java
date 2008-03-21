/**
 * 
 */
package com.yoursway.sadr.python.core.typeinferencing.constructs;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;

public final class SingleSubgoalContinuation implements Continuation {
    private final ValueInfoGoal varGoal;
    private final ValueInfoContinuation continuation;
    
    SingleSubgoalContinuation(ValueInfoGoal varGoal, ValueInfoContinuation continuation) {
        this.varGoal = varGoal;
        this.continuation = continuation;
    }
    
    public void provideSubgoals(SubgoalRequestor requestor) {
        requestor.subgoal(varGoal);
    }
    
    public void done(ContinuationRequestor requestor) {
        continuation.consume(varGoal.roughResult(), requestor);
    }
    
}
