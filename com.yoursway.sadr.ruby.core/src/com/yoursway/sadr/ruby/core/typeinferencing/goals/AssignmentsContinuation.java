/**
 * 
 */
package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContextSensitiveThing;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;

public final class AssignmentsContinuation extends AbstractContinuation {
    
    private final ExpressionValueInfoGoal[] goals;
    
    private final AssignmentInfo[] assignments;
    
    private final ContextSensitiveThing thing;
    
    private final ValueInfoContinuation continuation;
    
    public AssignmentsContinuation(ContextSensitiveThing thing, AssignmentInfo[] ass, RubyDynamicContext dc,
            InfoKind kind, ValueInfoContinuation continuation) {
        this.thing = thing;
        assignments = ass;
        this.continuation = continuation;
        goals = new ExpressionValueInfoGoal[assignments.length];
        for (int i = 0; i < assignments.length; i++) {
            RubyConstruct construct = assignments[i].rhs();
            goals[i] = new ExpressionValueInfoGoal(construct, dc, kind);
        }
    }
    
    public final Goal[] provideSubgoals() {
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