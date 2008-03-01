package com.yoursway.sadr.python.core.typeinferencing.goals;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Continuations;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.IterationContinuation;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.python.core.runtime.RubyBasicClass;
import com.yoursway.sadr.python.core.runtime.RubyField;
import com.yoursway.sadr.python.core.runtime.RubyMetaClass;
import com.yoursway.sadr.python.core.runtime.RubyMethod;
import com.yoursway.sadr.python.core.runtime.RubySourceMethod;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.VariableRequest;
import com.yoursway.sadr.python.core.typeinferencing.services.PropagationTracker;

public class FieldValueInfoGoal extends AbstractValueInfoGoal {
    
    private final RubyField field;
    private final InfoKind kind;
    
    public FieldValueInfoGoal(RubyField field, InfoKind kind) {
        this.field = field;
        this.kind = kind;
    }
    
    public void evaluate(ContinuationRequestor requestor) {
        final VariableRequest request = new VariableRequest(field, kind);
        Continuations.iterate(findPossibleWriters(), new IterationContinuation<RubyMethod>() {
            
            public void iteration(RubyMethod method, ContinuationRequestor requestor,
                    SimpleContinuation continuation) {
                if (method instanceof RubySourceMethod) {
                    RubySourceMethod sm = ((RubySourceMethod) method);
                    IConstruct construct = sm.scope().createConstruct();
                    PropagationTracker tracker = sm.scope().propagationTracker();
                    tracker.traverseEntirely(construct, request, requestor, continuation);
                } else {
                    continuation.run(requestor);
                }
            }
            
        }, requestor, new DelayedAssignmentsContinuation(request, kind, FieldValueInfoGoal.this));
    }
    
    private List<RubyMethod> findPossibleWriters() {
        RubyBasicClass container = field.container();
        ArrayList<RubyMethod> methods = new ArrayList<RubyMethod>();
        container.findMethodsByPrefix("", methods);
        if (container instanceof RubyMetaClass)
            ((RubyMetaClass) container).instanceClass().findMethodsByPrefix("", methods);
        return methods;
    }
    
    @Override
    public String describeParameters() {
        return "" + field;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        result = prime * result + ((kind == null) ? 0 : kind.hashCode());
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
        final FieldValueInfoGoal other = (FieldValueInfoGoal) obj;
        if (field == null) {
            if (other.field != null)
                return false;
        } else if (!field.equals(other.field))
            return false;
        if (kind == null) {
            if (other.kind != null)
                return false;
        } else if (!kind.equals(other.kind))
            return false;
        return true;
    }
    
    public int debugSlot() {
        return 3;
    }
    
    public Goal cloneGoal() {
        return new FieldValueInfoGoal(field, kind);
    }
    
}
