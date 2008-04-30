package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.python.core.runtime.PythonUtils.childrenOf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.Callable;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.CallsAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.CallsRequest;
import com.yoursway.sadr.python.core.typeinferencing.goals.CallInfo;
import com.yoursway.sadr.python.core.typeinferencing.goals.CallableReturnValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoUtils;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public abstract class CallC extends PythonConstructImpl<PythonCallExpression> implements CallsAffector {
    
    CallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
    }
    
    public List<PythonConstruct> arguments() {
        if (node.getArgs() == null) {
            return Collections.emptyList();
        }
        return Lists.transform(childrenOf(node.getArgs()), new Function<ASTNode, PythonConstruct>() {
            
            public PythonConstruct apply(ASTNode from) {
                return wrap(innerContext(), from);
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
        
        public CallablesReturnTypeCont(InfoKind infoKind, List<PythonConstruct> arguments,
                PythonDynamicContext dc, Callable[] callables, ValueInfo receiver,
                ValueInfoContinuation continuation) {
            this.infoKind = infoKind;
            this.callables = callables;
            this.receiver = receiver;
            this.continuation = continuation;
            argGoals = new HashMap<Callable, List<ValueInfoGoal>>(callables.length);
            //            for (Callable c : callables) {
            ArrayList<ValueInfoGoal> list = new ArrayList<ValueInfoGoal>(arguments.size());
            for (PythonConstruct arg : arguments)
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
                        retGoals[i] = new CallableReturnValueInfoGoal(receiver, callables[i], args, infoKind);
                    }
                }
                
                public Goal[] provideSubgoals() {
                    return retGoals;
                }
                
                public void done(ContinuationScheduler requestor) {
                    ValueInfoBuilder builder = new ValueInfoBuilder();
                    for (ValueInfoGoal goal : retGoals)
                        ValueInfoUtils.addResultOf(builder, goal, null);
                    continuation.consume(builder.build(), requestor);
                }
                
            });
        }
        
    }
    
    public void actOnCalls(CallsRequest request) {
        PythonConstruct receiver = receiver();
        if (receiver != null) {
            Collection<MumblaWumblaThreesome> swingerParty = receiver.mumblaWumbla();
            for (MumblaWumblaThreesome threesome : swingerParty)
                if (request.variable().name().equals(threesome.variableName()))
                    request.add(new CallInfo(threesome.wildcard(), this));
        }
    }
    
    public PythonConstruct receiver() {
        ASTNode receiver = node.getReceiver();
        if (receiver == null)
            return null;
        return wrap(innerContext(), receiver);
    }
    
}
