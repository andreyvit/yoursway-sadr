package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.ruby.core.runtime.Callable;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.CallableReturnValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public abstract class CallC extends DtlConstruct<CallExpression> {
    
    CallC(RubyStaticContext sc, CallExpression node) {
        super(sc, node);
    }
    
    protected class CallablesReturnTypeCont implements Continuation {
        private final Callable[] callables;
        private final ValueInfo receiver;
        //        final List<ValueInfoGoal> argGoals;
        final Map<Callable, List<ValueInfoGoal>> argGoals;
        
        private final ValueInfoContinuation continuation;
        private final InfoKind infoKind;
        
        public CallablesReturnTypeCont(InfoKind infoKind, ASTNode[] arguments, Callable[] callables,
                ValueInfo receiver, ValueInfoContinuation continuation) {
            this.infoKind = infoKind;
            this.callables = callables;
            this.receiver = receiver;
            this.continuation = continuation;
            argGoals = new HashMap<Callable, List<ValueInfoGoal>>(callables.length);
            //            for (Callable c : callables) {
            ArrayList<ValueInfoGoal> list = new ArrayList<ValueInfoGoal>(arguments.length);
            for (ASTNode arg : arguments)
                list.add(new ExpressionValueInfoGoal((Scope) rubyStaticContext(), arg, infoKind));
            argGoals.put(null, list);
            //            }
        }
        
        public void provideSubgoals(SubgoalRequestor requestor) {
            for (List<ValueInfoGoal> l : argGoals.values()) {
                for (Goal g : l)
                    requestor.subgoal(g);
            }
        }
        
        public void done(ContinuationRequestor requestor) {
            //            final ValueInfo[] args = new ValueInfo[argGoals.size()];
            //            for (int i = 0; i < args.length; i++)
            //                args[i] = argGoals.get(i).result(null);
            
            requestor.subgoal(new Continuation() {
                
                final ValueInfoGoal[] retGoals = new ValueInfoGoal[callables.length];
                
                {
                    for (int i = 0; i < callables.length; i++) {
                        List<ValueInfoGoal> list = argGoals.get(null);
                        ValueInfo[] args = new ValueInfo[list.size()];
                        int pos = 0;
                        for (ValueInfoGoal g : list)
                            args[pos++] = g.result(null);
                        retGoals[i] = new CallableReturnValueInfoGoal(callables[i], infoKind, receiver, args);
                    }
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
