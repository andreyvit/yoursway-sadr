/**
 * 
 */
package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.ContextSensitiveThing;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.ValueInfoContinuation;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public final class AssignmentsContinuation extends AbstractContinuation {
    
    private final ExpressionValueInfoGoal[] goals;
    
    private final AssignmentInfo[] assignments;
    
    private final ContextSensitiveThing thing;
    
    private final ValueInfoContinuation continuation;
    
    public AssignmentsContinuation(ContextSensitiveThing thing, AssignmentInfo[] ass, InfoKind kind,
            ValueInfoContinuation continuation) {
        this.thing = thing;
        assignments = ass;
        this.continuation = continuation;
        goals = new ExpressionValueInfoGoal[assignments.length];
        for (int i = 0; i < assignments.length; i++) {
            Construct<Scope, ASTNode> construct = assignments[i].construct();
            goals[i] = new ExpressionValueInfoGoal(construct.scope(), construct.node(), kind);
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
            ValueInfo result = goal.result(thing);
            builder.add(assignments[i].wildcard(), result);
        }
        continuation.consume(builder.build(), requestor);
    }
    
}