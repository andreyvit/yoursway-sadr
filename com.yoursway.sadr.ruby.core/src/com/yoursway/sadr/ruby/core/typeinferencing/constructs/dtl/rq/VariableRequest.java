package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl.rq;

import java.util.ArrayList;
import java.util.Collection;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.IConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.Request;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.AssignmentInfo;

public class VariableRequest implements Request {
    
    private final RubyVariable variable;
    
    private final Collection<AssignmentInfo> assigned = new ArrayList<AssignmentInfo>();
    
    public VariableRequest(RubyVariable variable, InfoKind infoKind) {
        this.variable = variable;
    }
    
    public RubyVariable variable() {
        return variable;
    }
    
    public void accept(IConstruct construct) {
        if (construct instanceof VariableAffector)
            ((VariableAffector) construct).actOnVariable(this);
    }
    
    public void add(AssignmentInfo info) {
        assigned.add(info);
    }
    
    public AssignmentInfo[] assigned() {
        return assigned.toArray(new AssignmentInfo[assigned.size()]);
    }
    
}
