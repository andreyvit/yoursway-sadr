/**
 * 
 */
package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.Sinner;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.ValueInfoContinuation;

public final class MergeConstructsValueInfosContinuation implements Continuation {
    
    private final ExpressionValueInfoGoal[] goals;
    private final Sinner victim;
    private final ValueInfoContinuation continuation;
    
    public MergeConstructsValueInfosContinuation(Sinner victim, Construct<?, ?>[] constructs, InfoKind kind,
            ValueInfoContinuation continuation) {
        this.victim = victim;
        this.continuation = continuation;
        goals = new ExpressionValueInfoGoal[constructs.length];
        for (int i = 0; i < constructs.length; i++)
            goals[i] = new ExpressionValueInfoGoal(constructs[i].scope(), constructs[i].node(), kind);
    }
    
    public void provideSubgoals(SubgoalRequestor requestor) {
        for (Goal goal : goals)
            requestor.subgoal(goal);
    }
    
    public void done(ContinuationRequestor requestor) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        for (ValueInfoGoal goal : goals)
            builder.addResultOf(goal, victim);
        continuation.consume(builder.build(), requestor);
    }
    
}
