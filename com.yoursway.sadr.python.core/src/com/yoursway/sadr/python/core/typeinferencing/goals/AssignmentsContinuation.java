/**
 * 
 */
package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.ContextSensitiveThing;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonDynamicContext;

public final class AssignmentsContinuation extends AbstractContinuation {
    
    private final ExpressionValueInfoGoal[] goals;
    
    private final AssignmentInfo[] assignments;
    
    private final ContextSensitiveThing victim;
    
    private final ValueInfoContinuation continuation;
    
    public AssignmentsContinuation(ContextSensitiveThing victim, AssignmentInfo[] ass, PythonDynamicContext dc,
            InfoKind kind, ValueInfoContinuation continuation) {
        this.victim = victim;
        assignments = ass;
        this.continuation = continuation;
        goals = new ExpressionValueInfoGoal[assignments.length];
        for (int i = 0; i < assignments.length; i++) {
            PythonConstruct construct = assignments[i].construct();
            goals[i] = new ExpressionValueInfoGoal(construct, dc, kind);
        }
    }
    
    public final void provideSubgoals(SubgoalRequestor requestor) {
        for (Goal goal : goals)
            requestor.subgoal(goal);
    }
    
    public final void done(ContinuationRequestor requestor) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        for (int i = 0; i < assignments.length; i++) {
            ValueInfoGoal goal = goals[i];
            ValueInfo result = goal.result(victim);
            builder.add(assignments[i].wildcard(), result);
        }
        continuation.consume(builder.build(), requestor);
    }
    
}