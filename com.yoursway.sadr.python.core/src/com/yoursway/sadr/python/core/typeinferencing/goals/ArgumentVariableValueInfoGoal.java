package com.yoursway.sadr.python.core.typeinferencing.goals;

import static com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetFactory.emptyTypeSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.ArrayListHashMultiMap;
import com.yoursway.sadr.python.core.ast.visitor.RubyControlFlowTraverser;
import com.yoursway.sadr.python.core.runtime.Callable;
import com.yoursway.sadr.python.core.runtime.RubyClass;
import com.yoursway.sadr.python.core.runtime.RubyUtils;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.VariableRequest;
import com.yoursway.sadr.python.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.python.core.typeinferencing.engine.ValueInfoContinuation;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.Wildcard;
import com.yoursway.sadr.python.core.typeinferencing.scopes.DtlArgumentVariable;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.PropagationTracker;
import com.yoursway.sadr.python.core.typeinferencing.services.ServicesMegapack;
import com.yoursway.sadr.python.core.typeinferencing.types.ClassType;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSet;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetBuilder;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetFactory;

public class ArgumentVariableValueInfoGoal extends AbstractValueInfoGoal {
    
    private final class ArgumentTypeByCallersInitiator implements ValueInfoContinuation {
        
        private final Callable callable;
        private final ValueInfoContinuation continuation;
        
        public ArgumentTypeByCallersInitiator(Callable callable, ValueInfoContinuation continuation) {
            this.callable = callable;
            this.continuation = continuation;
        }
        
        public void consume(ValueInfo result, ContinuationRequestor requestor) {
            final ValueInfoBuilder builder = new ValueInfoBuilder();
            builder.add(result);
            
            ValueInfo valueInfo = variable.valueInfo();
            if (valueInfo != null && !valueInfo.isEmpty())
                continueWith(builder, valueInfo, requestor);
            else {
                requestor.subgoal(new ArgumentTypeByCallersCont(callable, new ValueInfoContinuation() {
                    
                    public void consume(ValueInfo result, ContinuationRequestor requestor) {
                        continueWith(builder, result, requestor);
                    }
                    
                }));
            }
            
        }
        
        private void continueWith(ValueInfoBuilder builder, ValueInfo valueInfo,
                ContinuationRequestor requestor) {
            builder.add(valueInfo);
            continuation.consume(builder.build(), requestor);
        }
    }
    
    private class ArgumentTypeByCallersCont implements Continuation {
        
        private final Callable callable;
        private final MethodCallersGoal callersGoal;
        private final ValueInfoContinuation continuation;
        
        private ArgumentTypeByCallersCont(Callable callable, ValueInfoContinuation continuation) {
            this.callable = callable;
            this.continuation = continuation;
            callersGoal = new MethodCallersGoal(callable, megapack.searchService());
        }
        
        public void provideSubgoals(SubgoalRequestor requestor) {
            requestor.subgoal(callersGoal);
        }
        
        public void done(ContinuationRequestor requestor) {
            requestor.subgoal(new NodeValuesCont(collectValueNodes(), callable, continuation));
        }
        
        @SuppressWarnings("unchecked")
        private Construct<Scope, ASTNode>[] collectValueNodes() {
            List<Construct<Scope, ASTNode>> values = new ArrayList<Construct<Scope, ASTNode>>();
            int index = variable.index();
            CallersInfo callers = callersGoal.result(thou());
            for (Construct<Scope, CallExpression> caller : callers.callers()) {
                ASTNode[] args = RubyUtils.argumentsOf(caller.node());
                if (args.length > index) {
                    ASTNode arg = args[index];
                    Scope scope = RubyUtils.restoreSubscope(caller.scope(), arg);
                    values.add(new Construct<Scope, ASTNode>(scope, arg));
                }
            }
            return values.toArray(new Construct[values.size()]);
        }
    }
    
    private final class NodeValuesCont implements Continuation {
        
        private final Callable callable;
        private final ValueInfoGoal[] valueGoals;
        private final ValueInfoContinuation continuation;
        
        private NodeValuesCont(Construct<Scope, ASTNode>[] cs, Callable callable,
                ValueInfoContinuation continuation) {
            this.callable = callable;
            this.continuation = continuation;
            valueGoals = new ValueInfoGoal[cs.length];
            for (int i = 0; i < valueGoals.length; i++)
                valueGoals[i] = new ExpressionValueInfoGoal(cs[i].scope(), cs[i].node(), kind);
        }
        
        public void provideSubgoals(SubgoalRequestor requestor) {
            for (Goal goal : valueGoals)
                requestor.subgoal(goal);
        }
        
        public void done(ContinuationRequestor requestor) {
            ValueInfoBuilder builder = new ValueInfoBuilder();
            for (ValueInfoGoal goal : valueGoals)
                builder.addResultOf(goal, thou());
            if (!builder.looksEmpty())
                continuation.consume(builder.build(), requestor);
            else
                requestor.subgoal(new DetermineArgumentTypeByCalledMethodsCont(callable, continuation));
        }
    }
    
    private final class DetermineArgumentTypeByCalledMethodsCont implements Continuation {
        private final Callable callable;
        private final ValueInfoContinuation continuation;
        
        private DetermineArgumentTypeByCalledMethodsCont(Callable callable, ValueInfoContinuation continuation) {
            this.callable = callable;
            this.continuation = continuation;
        }
        
        public void provideSubgoals(SubgoalRequestor requestor) {
        }
        
        public void done(ContinuationRequestor requestor) {
            final CallsVisitor visitor = new CallsVisitor(variable);
            Construct<Scope, ASTNode> construct = callable.construct();
            new RubyControlFlowTraverser(thou(), construct.scope()).traverse(construct.node(), requestor,
                    visitor, new SimpleContinuation() {
                        
                        public void run(ContinuationRequestor requestor) {
                            CallInfo[] calls = visitor.calls();
                            
                            AbstractMultiMap<Wildcard, CallExpression> wildcardsToCalls = groupCallsByWildcard(calls);
                            ValueInfoBuilder builder = new ValueInfoBuilder();
                            for (Wildcard wildcard : wildcardsToCalls.keySet())
                                builder.add(wildcard, calculateTypeSet(wildcardsToCalls.get(wildcard)));
                            continuation.consume(builder.build(), requestor);
                        }
                        
                    });
        }
        
        private TypeSet calculateTypeSet(Collection<CallExpression> calls) {
            if (calls.isEmpty())
                return emptyTypeSet();
            Set<RubyClass> klasses = classLookup.findClassesByMethods(methodNames(calls));
            if (!klasses.isEmpty()) {
                TypeSetBuilder subbuilder = new TypeSetBuilder();
                for (RubyClass klass : klasses)
                    subbuilder.add(new ClassType(klass));
                TypeSet typeset = subbuilder.build();
                if (!typeset.isEmpty())
                    return typeset;
            }
            return TypeSetFactory.typeSetWith(new ClassType(classLookup.standardTypes().objectClass()));
        }
        
        private AbstractMultiMap<Wildcard, CallExpression> groupCallsByWildcard(CallInfo[] calls) {
            AbstractMultiMap<Wildcard, CallExpression> wildcardsToCalls = new ArrayListHashMultiMap<Wildcard, CallExpression>();
            for (CallInfo call : calls)
                wildcardsToCalls.put(call.getWildcard(), call.getNode());
            return wildcardsToCalls;
        }
    }
    
    private final DtlArgumentVariable variable;
    private final ClassLookup classLookup;
    private final ServicesMegapack megapack;
    private final InfoKind kind;
    
    public ArgumentVariableValueInfoGoal(DtlArgumentVariable variable, InfoKind kind,
            ServicesMegapack megapack) {
        this.variable = variable;
        this.kind = kind;
        this.megapack = megapack;
        this.classLookup = megapack.classLookup();
    }
    
    public DtlArgumentVariable variable() {
        return variable;
    }
    
    public InfoKind infoKind() {
        return kind;
    }
    
    public void evaluate(ContinuationRequestor requestor) {
        final Callable callable = variable.callable();
        final Construct<Scope, ASTNode> construct = callable.construct();
        //evaluateWithFlow(callable, construct, requestor);
        evaluateWithoutFlow(callable, construct, requestor);
    }
    
    //    private void evaluateWithFlow(final Callable callable, final Construct<Scope, ASTNode> construct,
    //            ContinuationRequestor requestor) {
    //        PropagationTracker tracker = construct.scope().propagationTracker();
    //        tracker.initiateBackwardPropagation(this, requestor, new ValueInfoContinuation() {
    //            
    //            public void consume(ValueInfo result, ContinuationRequestor requestor) {
    //                if (!result.isEmpty())
    //                    setResult(result);
    //                else
    //                    evaluateWithoutFlow(callable, construct, requestor);
    //            }
    //            
    //        });
    //    }
    
    private void evaluateWithoutFlow(final Callable callable, Construct<Scope, ASTNode> construct,
            ContinuationRequestor requestor) {
        IConstruct c = construct.asAnotherMyself();
        PropagationTracker tracker = construct.scope().propagationTracker();
        VariableRequest request = new VariableRequest(variable, kind);
        tracker.traverseEntirely(c, request, requestor, new DelayedAssignmentsContinuation(request, kind,
                new ArgumentTypeByCallersInitiator(callable, this)));
    }
    
    @Override
    public String describeParameters() {
        return "" + variable;
    }
    
    private static String[] methodNames(Collection<CallExpression> calls) {
        Collection<String> methodsColl = new ArrayList<String>(calls.size());
        for (CallExpression call : calls) {
            String name = call.getName();
            methodsColl.add(name);
        }
        return methodsColl.toArray(new String[methodsColl.size()]);
    }
    
    public int debugSlot() {
        return 0;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((kind == null) ? 0 : kind.hashCode());
        result = prime * result + ((variable == null) ? 0 : variable.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ArgumentVariableValueInfoGoal other = (ArgumentVariableValueInfoGoal) obj;
        if (kind == null) {
            if (other.kind != null)
                return false;
        } else if (!kind.equals(other.kind))
            return false;
        if (variable == null) {
            if (other.variable != null)
                return false;
        } else if (!variable.equals(other.variable))
            return false;
        return true;
    }
    
    public Goal cloneGoal() {
        return new ArgumentVariableValueInfoGoal(variable, kind, megapack);
    }
    
}
