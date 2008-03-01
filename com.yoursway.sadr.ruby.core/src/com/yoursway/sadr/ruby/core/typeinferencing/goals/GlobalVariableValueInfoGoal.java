package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import java.util.ArrayList;
import java.util.Collection;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyGlobalVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.ruby.core.typeinferencing.services.AssignmentsRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.services.SearchService;

public class GlobalVariableValueInfoGoal extends AbstractValueInfoGoal {
    
    private final RubyGlobalVariable variable;
    
    private final SearchService searchService;
    
    private final InfoKind kind;
    
    public GlobalVariableValueInfoGoal(RubyGlobalVariable variable, InfoKind kind, SearchService searchService) {
        this.variable = variable;
        this.kind = kind;
        this.searchService = searchService;
    }
    
    @Override
    public void copyAnswerFrom(Goal cachedGoal) {
        super.copyAnswerFrom(cachedGoal);
    }
    
    public void evaluate(ContinuationRequestor requestor) {
        final Collection<AssignmentInfo> assignments = new ArrayList<AssignmentInfo>(1000);
        searchService.findAssignments(variable.name(), new AssignmentsRequestor() {
            
            public void assignment(AssignmentInfo info, FileScope fileScope) {
                assignments.add(info);
            }
            
        });
        AssignmentInfo[] arr = assignments.toArray(new AssignmentInfo[assignments.size()]);
        requestor.subgoal(new AssignmentsContinuation(thou(), arr, kind, this));
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
        final GlobalVariableValueInfoGoal other = (GlobalVariableValueInfoGoal) obj;
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
        return 4;
    }
    
    public Goal cloneGoal() {
        return new GlobalVariableValueInfoGoal(variable, kind, searchService);
    }
    
}
