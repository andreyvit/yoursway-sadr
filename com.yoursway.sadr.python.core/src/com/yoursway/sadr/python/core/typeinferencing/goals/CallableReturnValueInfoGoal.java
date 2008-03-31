package com.yoursway.sadr.python.core.typeinferencing.goals;

import java.util.Arrays;

import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.python.core.runtime.Callable;
import com.yoursway.sadr.python.core.runtime.PythonBuiltinMethod;
import com.yoursway.sadr.python.core.runtime.PythonBuiltinProcedure;
import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonStaticContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.ReturnsRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.DynamicMethodScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.DynamicProcedureScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.MethodScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.ProcedureScope;

public class CallableReturnValueInfoGoal extends AbstractValueInfoGoal {
    
    private final Callable callable;
    private final ValueInfo receiver;
    private final ValueInfo[] arguments;
    private final InfoKind kind;
    
    public CallableReturnValueInfoGoal(Callable callable, InfoKind kind, ValueInfo receiver, ValueInfo[] args) {
        this.callable = callable;
        this.kind = kind;
        this.receiver = receiver;
        this.arguments = args;
    }
    
    public ContinuationRequestorCalledToken evaluate(ContinuationScheduler requestor) {
        if (callable.isBuiltin()) {
            if (callable instanceof PythonBuiltinMethod) {
                PythonBuiltinMethod method = (PythonBuiltinMethod) callable;
                return consume(method.evaluateBuiltin(receiver, arguments), requestor);
            } else {
                PythonBuiltinProcedure method = (PythonBuiltinProcedure) callable;
                return consume(method.evaluateBuiltin(arguments), requestor);
            }
        }
        PythonConstruct construct = callable.construct();
        if (construct != null) {
            PythonStaticContext staticScope = ((MethodDeclarationC) construct).methodScope();
            final PythonDynamicContext dc;
            if (staticScope instanceof MethodScope)
                dc = new DynamicMethodScope((MethodScope) staticScope, receiver, arguments);
            else
                dc = new DynamicProcedureScope((ProcedureScope) staticScope, arguments);
            
            final ReturnsRequest request = new ReturnsRequest();
            construct.staticContext().propagationTracker().traverseEntirely(construct, request, requestor,
                    new SimpleContinuation() {
                        
                        public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
                            return requestor.schedule(new MergeConstructsValueInfosContinuation(thing(),
                                    request.returns(), dc, kind, CallableReturnValueInfoGoal.this));
                        }
                        
                    });
        }
        
        // FIXME: just added for compilability. May be broken.
        return requestor.done();
    }
    
    @Override
    public String describeParameters() {
        return "" + callable;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(arguments);
        result = prime * result + ((callable == null) ? 0 : callable.hashCode());
        result = prime * result + ((kind == null) ? 0 : kind.hashCode());
        result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
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
        final CallableReturnValueInfoGoal other = (CallableReturnValueInfoGoal) obj;
        if (!Arrays.equals(arguments, other.arguments))
            return false;
        if (callable == null) {
            if (other.callable != null)
                return false;
        } else if (!callable.equals(other.callable))
            return false;
        if (kind == null) {
            if (other.kind != null)
                return false;
        } else if (!kind.equals(other.kind))
            return false;
        if (receiver == null) {
            if (other.receiver != null)
                return false;
        } else if (!receiver.equals(other.receiver))
            return false;
        return true;
    }
    
    public int debugSlot() {
        return 1;
    }
    
    public Goal cloneGoal() {
        return new CallableReturnValueInfoGoal(callable, kind, receiver, arguments);
    }
    
    @Override
    public boolean hasComplexUnnaturalRelationshipWithRecursion() {
        return true;
    }
    
}
