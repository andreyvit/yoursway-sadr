package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import static com.yoursway.sadr.ruby.core.typeinferencing.typesets.TypeSetFactory.emptyTypeSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.core.IValueInfo;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.ArrayListHashMultiMap;
import com.yoursway.sadr.ruby.core.runtime.Callable;
import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.runtime.RubyMethod;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.CallC;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.EmptyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.CallsRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.VariableRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.Wildcard;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.DtlArgumentVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.types.ClassType;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.TypeSet;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.TypeSetBuilder;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.TypeSetFactory;

public class ArgumentVariableValueInfoGoal extends AbstractValueInfoGoal {
    
    private final class ArgumentTypeByCallersInitiator implements ValueInfoContinuation {
        
        private final Callable callable;
        private final ValueInfoContinuation continuation;
        
        public ArgumentTypeByCallersInitiator(Callable callable, ValueInfoContinuation continuation) {
            this.callable = callable;
            this.continuation = continuation;
        }
        
        public ContinuationRequestorCalledToken consume(IValueInfo result, ContinuationScheduler requestor) {
            final ValueInfoBuilder builder = new ValueInfoBuilder();
            builder.add(ValueInfo.from(result));
            
            ValueInfo valueInfo = variable.valueInfo();
            if (valueInfo != null && !valueInfo.isEmpty())
                return continueWith(builder, valueInfo, requestor);
            else {
                return requestor.schedule(new ArgumentTypeByCallersCont(callable,
                        new ValueInfoContinuation() {
                            public ContinuationRequestorCalledToken consume(IValueInfo result,
                                    ContinuationScheduler requestor) {
                                return continueWith(builder, result, requestor);
                            }
                        }));
            }
        }
        
        private ContinuationRequestorCalledToken continueWith(ValueInfoBuilder builder, IValueInfo valueInfo,
                ContinuationScheduler requestor) {
            builder.add(ValueInfo.from(valueInfo));
            return continuation.consume(builder.build(), requestor);
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
        
        public void done(ContinuationScheduler requestor) {
            requestor.schedule(new NodeValuesCont(collectValueNodes(), callable, continuation));
        }
        
        private RubyConstruct[] collectValueNodes() {
            List<RubyConstruct> values = new ArrayList<RubyConstruct>();
            int index = variable.index();
            CallersInfo callers = callersGoal.result(thing());
            for (CallC caller : callers.callers()) {
                List<RubyConstruct> args = caller.arguments();
                if (args.size() > index) {
                    values.add(args.get(index));
                }
            }
            return values.toArray(new RubyConstruct[values.size()]);
        }
    }
    
    private final class NodeValuesCont implements Continuation {
        
        private final Callable callable;
        private final ValueInfoGoal[] valueGoals;
        private final ValueInfoContinuation continuation;
        
        private NodeValuesCont(RubyConstruct[] cs, Callable callable, ValueInfoContinuation continuation) {
            this.callable = callable;
            this.continuation = continuation;
            valueGoals = new ValueInfoGoal[cs.length];
            for (int i = 0; i < valueGoals.length; i++)
                valueGoals[i] = new ExpressionValueInfoGoal(cs[i], new EmptyDynamicContext(), kind);
        }
        
        public void provideSubgoals(SubgoalRequestor requestor) {
            for (Goal goal : valueGoals)
                requestor.subgoal(goal);
        }
        
        public void done(ContinuationScheduler requestor) {
            ValueInfoBuilder builder = new ValueInfoBuilder();
            for (ValueInfoGoal goal : valueGoals)
                builder.addResultOf(goal, thing());
            if (!builder.looksEmpty())
                continuation.consume(builder.build(), requestor);
            else
                requestor.schedule(new DetermineArgumentTypeByCalledMethodsCont(callable, continuation));
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
        
        public void done(ContinuationScheduler requestor) {
            RubyConstruct construct = callable.construct();
            final CallsRequest request = new CallsRequest(variable, kind);
            construct.staticContext().propagationTracker().traverseEntirely(construct, request, requestor,
                    new SimpleContinuation() {
                        
                        public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
                            CallInfo[] calls = request.calls();
                            
                            AbstractMultiMap<Wildcard, CallC> wildcardsToCalls = groupCallsByWildcard(calls);
                            ValueInfoBuilder builder = new ValueInfoBuilder();
                            for (Wildcard wildcard : wildcardsToCalls.keySet())
                                builder.add(wildcard, calculateTypeSet(wildcardsToCalls.get(wildcard)));
                            return continuation.consume(builder.build(), requestor);
                        }
                        
                    });
        }
        
        private TypeSet calculateTypeSet(Collection<CallC> calls) {
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
        
        private AbstractMultiMap<Wildcard, CallC> groupCallsByWildcard(CallInfo[] calls) {
            AbstractMultiMap<Wildcard, CallC> wildcardsToCalls = new ArrayListHashMultiMap<Wildcard, CallC>();
            for (CallInfo call : calls)
                wildcardsToCalls.put(call.getWildcard(), call.construct());
            return wildcardsToCalls;
        }
    }
    
    private final DtlArgumentVariable variable;
    private final ClassLookup classLookup;
    private final RubyStaticContext megapack;
    private final InfoKind kind;
    
    public ArgumentVariableValueInfoGoal(DtlArgumentVariable variable, InfoKind kind,
            RubyStaticContext megapack) {
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
    
    public ContinuationRequestorCalledToken evaluate(ContinuationScheduler requestor) {
        final Callable callable = variable.callable();
        final RubyConstruct construct = callable.construct();
        return evaluateWithoutFlow(callable, construct, requestor);
    }
    
    //    private void evaluateWithFlow(final Callable callable, final RubyConstruct construct,
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
    
    private ContinuationRequestorCalledToken evaluateWithoutFlow(final Callable callable,
            RubyConstruct construct, ContinuationScheduler requestor) {
        
        if (callable instanceof RubyMethod) {
            if (variable.index() == 0) {
                // TODO: need a dynamic context here 
                ValueInfo selfType = construct.staticContext().selfType();
                if (selfType != null)
                    return consume(selfType, requestor);
                else
                    return consume(ValueInfo.emptyValueInfo(), requestor);
            }
        }
        
        VariableRequest request = new VariableRequest(variable, kind);
        return construct.staticContext().propagationTracker().traverseEntirely(
                construct,
                request,
                requestor,
                new DelayedAssignmentsContinuation(request, new EmptyDynamicContext(), kind,
                        new ArgumentTypeByCallersInitiator(callable, this)));
    }
    
    @Override
    public String describeParameters() {
        return "" + variable;
    }
    
    private String[] methodNames(Collection<CallC> calls) {
        Collection<String> methodsColl = new ArrayList<String>(calls.size());
        for (CallC call : calls) {
            CallExpression node = call.node();
            String name = node.getName();
            if (name != null)
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
