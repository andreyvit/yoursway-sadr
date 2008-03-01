package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.ruby.core.runtime.Callable;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.StaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.ValueInfoContinuation;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.CallableReturnValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;

public abstract class CallC extends DtlConstruct<CallExpression> {
    
    CallC(StaticContext sc, CallExpression node) {
        super(sc, node);
    }
    
    protected static class CallablesReturnTypeCont implements Continuation {
        private final Callable[] callables;
        private final ValueInfo receiver;
        final List<ValueInfoGoal> argGoals;
        private final ValueInfoContinuation continuation;
        private final InfoKind infoKind;
        
        public CallablesReturnTypeCont(InfoKind infoKind, ASTNode[] arguments, Callable[] callables,
                ValueInfo receiver, ValueInfoContinuation continuation) {
            this.infoKind = infoKind;
            this.callables = callables;
            this.receiver = receiver;
            this.continuation = continuation;
            argGoals = new ArrayList<ValueInfoGoal>(arguments.length);
            //            for (ASTNode arg : arguments)
            //                argGoals.add(new ExpressionValueInfoGoal(scope, arg, kind));
        }
        
        public void provideSubgoals(SubgoalRequestor requestor) {
            for (Goal g : argGoals)
                requestor.subgoal(g);
        }
        
        public void done(ContinuationRequestor requestor) {
            final ValueInfo[] args = new ValueInfo[argGoals.size()];
            for (int i = 0; i < args.length; i++)
                args[i] = argGoals.get(i).result(null);
            
            requestor.subgoal(new Continuation() {
                
                final ValueInfoGoal[] retGoals = new ValueInfoGoal[callables.length];
                
                {
                    for (int i = 0; i < callables.length; i++)
                        retGoals[i] = new CallableReturnValueInfoGoal(callables[i], infoKind, receiver, args);
                }
                
                public void provideSubgoals(SubgoalRequestor requestor) {
                    for (Goal goal : retGoals)
                        requestor.subgoal(goal);
                }
                
                public void done(ContinuationRequestor requestor) {
                    ValueInfoBuilder builder = new ValueInfoBuilder();
                    for (ValueInfoGoal goal : retGoals)
                        builder.addResultOf(goal, null);
                    continuation.consume(builder.build(), requestor);
                }
                
            });
        }
        
    }
    
}
