package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.ruby.core.runtime.RubyLocalVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.EmptyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.VariableRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public class LocalVariableValueInfoGoal extends AbstractValueInfoGoal {
    
    private final RubyLocalVariable variable;
    private final InfoKind kind;
    
    public LocalVariableValueInfoGoal(RubyLocalVariable variable, InfoKind kind) {
        this.variable = variable;
        this.kind = kind;
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
        RubyConstruct construct = scope.createConstruct();
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
        final LocalVariableValueInfoGoal other = (LocalVariableValueInfoGoal) obj;
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
        return new LocalVariableValueInfoGoal(variable, kind);
    }
    
}
