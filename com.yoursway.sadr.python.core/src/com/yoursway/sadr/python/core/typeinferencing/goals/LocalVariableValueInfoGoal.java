package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.RubyLocalVariable;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.VariableRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python.core.typeinferencing.services.PropagationTracker;

public class LocalVariableValueInfoGoal extends AbstractValueInfoGoal {
    
    private final RubyLocalVariable variable;
    private final InfoKind kind;
    
    public LocalVariableValueInfoGoal(RubyLocalVariable variable, InfoKind kind) {
        this.variable = variable;
        this.kind = kind;
    }
    
    public void evaluate(ContinuationRequestor requestor) {
        Scope scope = variable.scope();
        //        ASTNode node = variable.container().node();
        PropagationTracker tracker = scope.propagationTracker();
        
        IConstruct construct = scope.createConstruct();
        final VariableRequest request = new VariableRequest(variable, kind);
        tracker.traverseEntirely(construct, request, requestor, new DelayedAssignmentsContinuation(request,
                kind, this));
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
