package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.python.core.runtime.PythonScopedVariable;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.VariableRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.constructs.EmptyDynamicContext;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonDynamicContext;

public class ScopedVariableValueInfoGoal extends AbstractValueInfoGoal {
    
    private final PythonScopedVariable variable;
    private final InfoKind kind;
    private final PythonDynamicContext dc;
    
    public ScopedVariableValueInfoGoal(PythonScopedVariable variable, InfoKind kind, PythonDynamicContext dc) {
        this.variable = variable;
        this.kind = kind;
        this.dc = dc;
    }
    
    public ContinuationRequestorCalledToken evaluate(ContinuationScheduler requestor) {
        return evaluateWithFlow(requestor);
    }
    
    private ContinuationRequestorCalledToken evaluateWithFlow(ContinuationScheduler requestor) {
        SimpleContinuation withoutFlowContinuation = new SimpleContinuation() {
            
            public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
                return evaluateWithoutFlow(requestor);
            }
            
        };
        return withoutFlowContinuation.run(requestor);
        //        Scope scope = variable.scope();
        //        BackwardVariableRequest request = new BackwardVariableRequest(variable, kind);
        //        return scope.propagationTracker().traverseBackwardByControlFlowFromLastConstructBoundGoalConstruct(
        //                request,
        //                requestor,
        //                new DelayedAssignmentsContinuation(request, new EmptyDynamicContext(), kind,
        //                        new TryAnotherThingContinuation(withoutFlowContinuation, this)));
    }
    
    private ContinuationRequestorCalledToken evaluateWithoutFlow(ContinuationScheduler requestor) {
        Scope scope = variable.scope();
        PythonConstruct construct = scope.createConstruct();
        final VariableRequest request = new VariableRequest(variable, kind);
        return scope.propagationTracker().traverseEntirely(construct, request, requestor,
                new DelayedAssignmentsContinuation(request, new EmptyDynamicContext(), kind, this));
    }
    
    @Override
    public String describeParameters() {
        return "" + variable;
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
        final ScopedVariableValueInfoGoal other = (ScopedVariableValueInfoGoal) obj;
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
    
    public int debugSlot() {
        return 5;
    }
    
    public Goal cloneGoal() {
        return new ScopedVariableValueInfoGoal(variable, kind, dc);
    }
    
}
