package com.yoursway.sadr.python.core.typeinferencing.goals;

import static com.yoursway.sadr.blocks.foundation.typesets.TypeSetFactory.emptyTypeSet;
import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.blocks.foundation.typesets.TypeSet;
import com.yoursway.sadr.blocks.foundation.typesets.TypeSetBuilder;
import com.yoursway.sadr.blocks.foundation.typesets.TypeSetFactory;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.foundation.wildcards.Wildcard;
import com.yoursway.sadr.core.IValueInfo;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.ArrayListHashMultiMap;
import com.yoursway.sadr.python.core.runtime.Callable;
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.PythonMethod;
import com.yoursway.sadr.python.core.typeinferencing.constructs.CallC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.EmptyDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.CallsRequest;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.VariableRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.DtlArgumentVariable;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.python.core.typeinferencing.types.InstanceType;

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
        
        public Goal[] provideSubgoals() {
            return new Goal[] { callersGoal };
        }
        
        public void done(ContinuationScheduler requestor) {
            requestor.schedule(new NodeValuesCont(collectValueNodes(), callable, continuation));
        }
        
        private PythonConstruct[] collectValueNodes() {
            List<PythonConstruct> values = new ArrayList<PythonConstruct>();
            int index = variable.index();
            if (callable instanceof PythonMethod)
                index--;
            CallersInfo callers = callersGoal.result(thing());
            for (CallC caller : callers.callers()) {
                List<PythonConstruct> args = caller.arguments();
                if (args.size() > index)
                    values.add(args.get(index));
            }
            return values.toArray(new PythonConstruct[values.size()]);
        }
    }
    
    private final class NodeValuesCont implements Continuation {
        
        private final Callable callable;
        private final ValueInfoGoal[] valueGoals;
        private final ValueInfoContinuation continuation;
        
        private NodeValuesCont(PythonConstruct[] cs, Callable callable, ValueInfoContinuation continuation) {
            this.callable = callable;
            this.continuation = continuation;
            valueGoals = new ValueInfoGoal[cs.length];
            for (int i = 0; i < valueGoals.length; i++)
                valueGoals[i] = new ExpressionValueInfoGoal(cs[i], new EmptyDynamicContext(), kind);
        }
        
        public Goal[] provideSubgoals() {
            return valueGoals;
        }
        
        public void done(ContinuationScheduler requestor) {
            ValueInfoBuilder builder = new ValueInfoBuilder();
            for (ValueInfoGoal goal : valueGoals)
                ValueInfoUtils.addResultOf(builder, goal, thing());
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
        
        public Goal[] provideSubgoals() {
            return new Goal[] {};
        }
        
        public void done(ContinuationScheduler requestor) {
            PythonConstruct construct = callable.construct();
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
            Set<PythonClass> klasses = classLookup.findClassesByMethods(methodNames(calls));
            if (!klasses.isEmpty()) {
                TypeSetBuilder subbuilder = new TypeSetBuilder();
                for (PythonClass klass : klasses)
                    subbuilder.add(new InstanceType(klass));
                TypeSet typeset = subbuilder.build();
                if (!typeset.isEmpty())
                    return typeset;
            }
            return TypeSetFactory.typeSetWith(new InstanceType(classLookup.standardTypes().objectClass()));
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
    private final Scope megapack;
    private final InfoKind kind;
    
    public ArgumentVariableValueInfoGoal(DtlArgumentVariable variable, InfoKind kind, Scope megapack) {
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
        final PythonConstruct construct = callable.construct();
        //evaluateWithFlow(callable, construct, requestor);
        return evaluateWithoutFlow(callable, construct, requestor);
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
    
    private ContinuationRequestorCalledToken evaluateWithoutFlow(final Callable callable,
            PythonConstruct construct, ContinuationScheduler requestor) {
        if (callable instanceof PythonMethod) {
            if (variable.index() == 0) {
                // TODO: need a dynamic context here 
                ValueInfo selfType = construct.staticContext().selfType();
                if (selfType != null)
                    return consume(selfType, requestor);
                else
                    return consume(emptyValueInfo(), requestor);
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
            PythonCallExpression node = call.node();
            String name = node.getMethodName();
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
