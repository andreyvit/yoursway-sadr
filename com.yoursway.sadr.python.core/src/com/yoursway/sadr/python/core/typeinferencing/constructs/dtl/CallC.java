package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.python.core.runtime.RubyUtils.childrenOf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.python.parser.ast.expressions.ExtendedVariableReference;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.runtime.Callable;
import com.yoursway.sadr.python.core.runtime.RubyUtils;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.CallsAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.CallsRequest;
import com.yoursway.sadr.python.core.typeinferencing.goals.CallInfo;
import com.yoursway.sadr.python.core.typeinferencing.goals.CallableReturnValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.Wildcard;

public abstract class CallC extends PythonConstructImpl<CallExpression> implements CallsAffector {
    
    private final ExtendedVariableReference originalNode;
    
    CallC(PythonStaticContext sc, CallExpression node, ExtendedVariableReference originalNode) {
        super(sc, node);
        this.originalNode = originalNode;
    }
    
    public List<PythonConstruct> arguments() {
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
    
    public void actOnCalls(CallsRequest request) {
        ASTNode receiver = node.getReceiver();
        if (receiver != null) {
            ASTNode terminal = RubyUtils.assignmentTerminalNode(receiver);
            if (matches(request, terminal)) {
                Wildcard wildcard = RubyUtils.assignmentWildcardExpression(receiver);
                request.add(new CallInfo(wildcard, this));
            }
        }
    }
    
    protected boolean matches(CallsRequest request, ASTNode terminal) {
        boolean doit = false;
        if (terminal instanceof VariableReference) {
            doit = (((VariableReference) terminal).getName().equalsIgnoreCase(request.variable().name()));
        } else {
            //if (terminal instanceof RubyColonExpression) {
            ; // TODO
        }
        return doit;
    }
    
    @Override
    protected boolean isNode(ASTNode node) {
        return originalNode == node || this.node == node;
    }
    
    public PythonConstruct receiver() {
        ASTNode receiver = node.getReceiver();
        if (receiver == null)
            return null;
        return wrap(innerContext(), receiver);
    }
    
}
