package com.yoursway.sadr.python.core.typeinferencing.goals;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonBasicClass;
import com.yoursway.sadr.python.core.runtime.PythonUtils;
import com.yoursway.sadr.python.core.typeinferencing.constructs.EmptyDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;

public class FilterByReceiversContinuation implements Continuation {
    
    private final ExpressionValueInfoGoal[] goals;
    private final PythonBasicClass klass;
    private final AssignmentInfo[] assignments;
    private final InfoKind infoKind;
    private final ValueInfoContinuation continuation;
    
    public FilterByReceiversContinuation(PythonBasicClass klass, AssignmentInfo[] assignments,
            PythonDynamicContext dc, InfoKind infoKind, ValueInfoContinuation continuation) {
        this.klass = klass;
        this.assignments = assignments;
        this.infoKind = infoKind;
        this.continuation = continuation;
        goals = new ExpressionValueInfoGoal[assignments.length];
        for (int i = 0; i < assignments.length; i++) {
            PythonConstruct construct = assignments[i].receiver();
            goals[i] = new ExpressionValueInfoGoal(construct, dc, infoKind);
        }
    }
    
    public Goal[] provideSubgoals() {
        return goals;
    }
    
    public void done(ContinuationScheduler requestor) {
        List<AssignmentInfo> thisFieldAssignments = newArrayList();
        for (int i = 0; i < goals.length; i++)
            checkCandidateReceiver(thisFieldAssignments, assignments[i], goals[i].result(null));
        requestor.schedule((Continuation) new AssignmentsContinuation(null, thisFieldAssignments
                .toArray(new AssignmentInfo[thisFieldAssignments.size()]), new EmptyDynamicContext(),
                infoKind, continuation));
    }
    
    private void checkCandidateReceiver(List<AssignmentInfo> thisFieldAssignments, AssignmentInfo assignment,
            ValueInfo result) {
        for (Type type : result.getTypeSet().containedTypes()) {
            PythonBasicClass candidateKlass = PythonUtils.unwrapType(type);
            if (candidateKlass != null)
                if (PythonUtils.isXDerivedFromY(candidateKlass, klass))
                    thisFieldAssignments.add(assignment);
        }
    }
}
