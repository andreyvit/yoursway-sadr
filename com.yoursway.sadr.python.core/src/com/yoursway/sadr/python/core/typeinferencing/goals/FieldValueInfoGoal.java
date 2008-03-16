package com.yoursway.sadr.python.core.typeinferencing.goals;

import static com.google.common.collect.Lists.newArrayList;
import static com.yoursway.sadr.engine.util.Lists.filter;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.Predicate;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonField;
import com.yoursway.sadr.python.core.typeinferencing.constructs.EmptyDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.python.core.typeinferencing.services.AssignmentsRequestor;
import com.yoursway.sadr.python.core.typeinferencing.services.SearchService;

public class FieldValueInfoGoal extends AbstractValueInfoGoal {
    
    private final PythonField field;
    private final InfoKind kind;
    private final SearchService searchService;
    
    private final static Predicate<AssignmentInfo> RECEIVER_NOT_NULL = new Predicate<AssignmentInfo>() {
        
        public boolean apply(AssignmentInfo t) {
            return t.receiver() != null;
        }
        
    };
    
    public FieldValueInfoGoal(PythonField field, InfoKind kind, SearchService searchService) {
        this.field = field;
        this.kind = kind;
        this.searchService = searchService;
    }
    
    public void evaluate(ContinuationRequestor requestor) {
        ArrayList<AssignmentInfo> assignments = findAssignmentsUsingSearch();
        AssignmentInfo[] arr = assignments.toArray(new AssignmentInfo[assignments.size()]);
        requestor.subgoal(new FilterByReceiversContinuation(field.container(), arr,
                new EmptyDynamicContext(), kind, this));
    }
    
    private ArrayList<AssignmentInfo> findAssignmentsUsingSearch() {
        final Collection<AssignmentInfo> assignments = new ArrayList<AssignmentInfo>(1000);
        searchService.findAssignments(field.name(), new AssignmentsRequestor() {
            
            public void assignment(AssignmentInfo info, FileScope fileScope) {
                assignments.add(info);
            }
            
        });
        return newArrayList(filter(assignments, RECEIVER_NOT_NULL));
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
        return new FieldValueInfoGoal(field, kind, searchService);
    }
    
}
