package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import static com.yoursway.sadr.ruby.core.runtime.RubyUtils.childrenOf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ruby.ast.RubyCallArgument;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.Callable;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.CallsAffector;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.CallsRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.AccessInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.CallInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.CallableReturnValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;

public abstract class CallC extends RubyConstructImpl<CallExpression> implements CallsAffector {
    
    CallC(RubyStaticContext sc, CallExpression node) {
        super(sc, node);
    }
    
    public List<RubyConstruct> arguments() {
        if (node.getArgs() == null) {
            return Collections.emptyList();
        }
        return Lists.transform(childrenOf(node.getArgs()), new Function<ASTNode, RubyConstruct>() {
            
            public RubyConstruct apply(ASTNode from) {
                RubyCallArgument arg = (RubyCallArgument) from;
                return wrap(innerContext(), arg.getValue());
            }
            
        });
    }
    
    protected class CallablesReturnTypeCont implements Continuation {
        private final Callable[] callables;
        private final ValueInfo receiver;
        //        final List<ValueInfoGoal> argGoals;
        final Map<Callable, List<ValueInfoGoal>> argGoals;
        
        private final ValueInfoContinuation continuation;
        private final InfoKind infoKind;
        
        public CallablesReturnTypeCont(InfoKind infoKind, List<RubyConstruct> arguments,
                RubyDynamicContext dc, Callable[] callables, ValueInfo receiver,
                ValueInfoContinuation continuation) {
            this.infoKind = infoKind;
            this.callables = callables;
            this.receiver = receiver;
            this.continuation = continuation;
            argGoals = new HashMap<Callable, List<ValueInfoGoal>>(callables.length);
            //            for (Callable c : callables) {
            ArrayList<ValueInfoGoal> list = new ArrayList<ValueInfoGoal>(arguments.size());
            for (RubyConstruct arg : arguments)
                list.add(new ExpressionValueInfoGoal(arg, dc, infoKind));
            argGoals.put(null, list);
            //            }
        }
        
        public Goal[] provideSubgoals() {
            ArrayList<Goal> allGoals = new ArrayList<Goal>();
            for (List<ValueInfoGoal> l : argGoals.values()) {
                allGoals.addAll(l);
            }
            return allGoals.toArray(new Goal[] {});
        }
        
        public void done(ContinuationScheduler requestor) {
            //            final ValueInfo[] args = new ValueInfo[argGoals.size()];
            //            for (int i = 0; i < args.length; i++)
            //                args[i] = argGoals.get(i).result(null);
            
            requestor.schedule(new Continuation() {
                
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
                
                public Goal[] provideSubgoals() {
                    return retGoals;
                }
                
                public void done(ContinuationScheduler requestor) {
                    ValueInfoBuilder builder = new ValueInfoBuilder();
                    for (ValueInfoGoal goal : retGoals)
                        builder.addResultOf(goal, null);
                    continuation.consume(builder.build(), requestor);
                }
                
            });
        }
        
    }
    
    public void actOnCalls(CallsRequest request) {
        RubyConstruct receiver = receiver();
        if (receiver != null) {
            Collection<AccessInfo> accessInfos = receiver.accessInfos();
            for (AccessInfo access : accessInfos)
                if (request.variable().name().equals(access.variableName()))
                    request.add(new CallInfo(access.wildcard(), this));
        }
    }
    
    public RubyConstruct receiver() {
        ASTNode receiver = node.getReceiver();
        if (receiver == null)
            return null;
        return wrap(innerContext(), receiver);
    }
    
}
