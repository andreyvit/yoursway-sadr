package com.yoursway.sadr.python.core.typeinferencing.goals;

import java.util.Arrays;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.python.core.runtime.Callable;
import com.yoursway.sadr.python.core.runtime.PythonBuiltinMethod;
import com.yoursway.sadr.python.core.runtime.PythonBuiltinProcedure;
import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.ReturnsRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.DynamicMethodScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.DynamicProcedureScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.MethodScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.ProcedureScope;

public class CallableReturnValueInfoGoal extends AbstractValueInfoGoal {
    
    private final ValueInfo callee;
    private final Callable calledMethod;
    private final ValueInfo[] arguments;
    private final InfoKind kind;
    
    public CallableReturnValueInfoGoal(ValueInfo callee, Callable calledMethod, ValueInfo[] args,
            InfoKind kind) {
        this.callee = callee;
        this.calledMethod = calledMethod;
        this.arguments = args;
        this.kind = kind;
    }
    
    public ContinuationRequestorCalledToken evaluate(ContinuationScheduler requestor) {
        if (calledMethod.isBuiltin()) {
            if (calledMethod instanceof PythonBuiltinMethod) {
                PythonBuiltinMethod method = (PythonBuiltinMethod) calledMethod;
                return consume(method.evaluateBuiltin(callee, arguments), requestor);
            } else {
                PythonBuiltinProcedure method = (PythonBuiltinProcedure) calledMethod;
                return consume(method.evaluateBuiltin(arguments), requestor);
            }
        }
        PythonConstruct construct = calledMethod.construct();
        if (construct == null)
            throw new AssertionError("Callable must either be a built-in or have a construct");
        Scope staticScope = ((MethodDeclarationC) construct).methodScope();
        final PythonDynamicContext dc;
        if (staticScope instanceof MethodScope)
            dc = new DynamicMethodScope((MethodScope) staticScope, callee, arguments);
        else
            dc = new DynamicProcedureScope((ProcedureScope) staticScope, arguments);
        
        final ReturnsRequest request = new ReturnsRequest();
        return construct.staticContext().propagationTracker().traverseEntirely(construct, request, requestor,
                new SimpleContinuation() {
                    
                    public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
                        return requestor.schedule(new MergeConstructsValueInfosContinuation(thing(), request
                                .returns(), dc, kind, CallableReturnValueInfoGoal.this));
                    }
                    
                });
    }
    
    @Override
    public String describeParameters() {
        return "" + calledMethod;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(arguments);
        result = prime * result + ((calledMethod == null) ? 0 : calledMethod.hashCode());
        result = prime * result + ((kind == null) ? 0 : kind.hashCode());
        result = prime * result + ((callee == null) ? 0 : callee.hashCode());
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
        if (calledMethod == null) {
            if (other.calledMethod != null)
                return false;
        } else if (!calledMethod.equals(other.calledMethod))
            return false;
        if (kind == null) {
            if (other.kind != null)
                return false;
        } else if (!kind.equals(other.kind))
            return false;
        if (callee == null) {
            if (other.callee != null)
                return false;
        } else if (!callee.equals(other.callee))
            return false;
        return true;
    }
    
    public int debugSlot() {
        return 1;
    }
    
    public Goal cloneGoal() {
        return new CallableReturnValueInfoGoal(callee, calledMethod, arguments, kind);
    }
    
    @Override
    public boolean hasComplexUnnaturalRelationshipWithRecursion() {
        return true;
    }
    
}
