package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonLocalVariable;
import com.yoursway.sadr.python.core.typeinferencing.constructs.EmptyDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.VariableRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class LocalVariableValueInfoGoal extends AbstractValueInfoGoal {
    
    private final PythonLocalVariable variable;
    private final InfoKind kind;
    
    public LocalVariableValueInfoGoal(PythonLocalVariable variable, InfoKind kind) {
        this.variable = variable;
        this.kind = kind;
    }
    
    public void evaluate(ContinuationRequestor requestor) {
        Scope scope = variable.scope();
        PythonConstruct construct = scope.createConstruct();
        final VariableRequest request = new VariableRequest(variable, kind);
        scope.propagationTracker().traverseEntirely(construct, request, requestor,
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
