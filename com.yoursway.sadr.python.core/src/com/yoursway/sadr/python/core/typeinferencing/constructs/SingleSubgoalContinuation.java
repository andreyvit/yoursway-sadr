/**
 * 
 */
package com.yoursway.sadr.python.core.typeinferencing.constructs;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;

public final class SingleSubgoalContinuation implements Continuation {
    private final ValueInfoGoal varGoal;
    private final ValueInfoContinuation continuation;
    
    SingleSubgoalContinuation(ValueInfoGoal varGoal, ValueInfoContinuation continuation) {
        this.varGoal = varGoal;
        this.continuation = continuation;
    }
    
    public Goal[] provideSubgoals() {
        return new Goal[] { varGoal };
    }
    
    public void done(ContinuationScheduler requestor) {
        continuation.consume(varGoal.roughResult(), requestor);
    }
    
}
