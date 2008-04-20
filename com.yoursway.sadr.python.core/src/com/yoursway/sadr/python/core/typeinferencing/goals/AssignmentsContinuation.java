/**
 * 
 */
package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContextSensitiveThing;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;

public final class AssignmentsContinuation extends AbstractContinuation {
    
    private final ExpressionValueInfoGoal[] goals;
    
    private final AssignmentInfo[] assignments;
    
    private final ContextSensitiveThing thing;
    
    private final ValueInfoContinuation continuation;
    
    public AssignmentsContinuation(ContextSensitiveThing thing, AssignmentInfo[] ass,
            PythonDynamicContext dc, InfoKind kind, ValueInfoContinuation continuation) {
        this.thing = thing;
        assignments = ass;
        this.continuation = continuation;
        goals = new ExpressionValueInfoGoal[assignments.length];
        for (int i = 0; i < assignments.length; i++) {
            PythonConstruct construct = assignments[i].rhs();
            goals[i] = new ExpressionValueInfoGoal(construct, dc, kind);
        }
    }
    
    public Goal[] provideSubgoals() {
        return goals;
    }
    
    public final void done(ContinuationScheduler requestor) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        for (int i = 0; i < assignments.length; i++) {
            ValueInfoGoal goal = goals[i];
            ValueInfo result = goal.result(thing);
            builder.add(assignments[i].wildcard(), result);
        }
        continuation.consume(builder.build(), requestor);
    }
    
}