/**
 * 
 */
package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContextSensitiveThing;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;

public final class MergeConstructsValueInfosContinuation implements Continuation {
    
    private final ExpressionValueInfoGoal[] goals;
    private final ContextSensitiveThing thing;
    private final ValueInfoContinuation continuation;
    
    public MergeConstructsValueInfosContinuation(ContextSensitiveThing thing, PythonConstruct[] constructs,
            PythonDynamicContext dc, InfoKind kind, ValueInfoContinuation continuation) {
        this.thing = thing;
        this.continuation = continuation;
        goals = new ExpressionValueInfoGoal[constructs.length];
        for (int i = 0; i < constructs.length; i++)
            goals[i] = new ExpressionValueInfoGoal(constructs[i], dc, kind);
    }
    
    public Goal[] provideSubgoals() {
        return goals;
    }
    
    public void done(ContinuationScheduler requestor) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        for (ValueInfoGoal goal : goals)
            ValueInfoUtils.addResultOf(builder, goal, thing);
        continuation.consume(builder.build(), requestor);
    }
    
}
