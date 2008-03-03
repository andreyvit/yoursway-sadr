/**
 * 
 */
package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.ContextSensitiveThing;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;

public final class MergeConstructsValueInfosContinuation implements Continuation {
    
    private final ExpressionValueInfoGoal[] goals;
    private final ContextSensitiveThing victim;
    private final ValueInfoContinuation continuation;
    
    public MergeConstructsValueInfosContinuation(ContextSensitiveThing victim, PythonConstruct[] constructs,
            PythonDynamicContext dc, InfoKind kind, ValueInfoContinuation continuation) {
        this.victim = victim;
        this.continuation = continuation;
        goals = new ExpressionValueInfoGoal[constructs.length];
        for (int i = 0; i < constructs.length; i++)
            goals[i] = new ExpressionValueInfoGoal(constructs[i], dc, kind);
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
