/**
 * 
 */
package com.yoursway.sadr.python.core.typeinferencing.goals;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.collect.Lists;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.runtime.PythonField;

public final class MergeFieldsValueInfosContinuation implements Continuation {
    
    private final ValueInfoGoal[] goals;
    private final ValueInfoContinuation continuation;
    
    public MergeFieldsValueInfosContinuation(Collection<PythonField> fields, InfoKind kind,
            ValueInfoContinuation continuation) {
        this.continuation = continuation;
        ArrayList<PythonField> flist = Lists.newArrayList(fields);
        goals = new FieldValueInfoGoal[flist.size()];
        for (int i = 0; i < flist.size(); i++)
            goals[i] = new FieldValueInfoGoal(flist.get(i), kind);
    }
    
    public void provideSubgoals(SubgoalRequestor requestor) {
        for (Goal goal : goals)
            requestor.subgoal(goal);
    }
    
    public void done(ContinuationRequestor requestor) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        for (ValueInfoGoal goal : goals)
            builder.addResultOf(goal, null);
        continuation.consume(builder.build(), requestor);
    }
    
}
