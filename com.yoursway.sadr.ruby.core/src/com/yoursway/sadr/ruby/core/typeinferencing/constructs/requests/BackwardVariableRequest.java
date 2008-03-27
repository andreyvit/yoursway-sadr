package com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.BackwardRequest;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.AssignmentInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ThingAccessInfo;

public class BackwardVariableRequest implements
        BackwardRequest<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode>,
        AssignmentInfoRequestor, AssignmentInfoProvider {
    
    private final RubyVariable variable;
    
    private final Collection<AssignmentInfo> assigned = new ArrayList<AssignmentInfo>();
    
    public BackwardVariableRequest(RubyVariable variable, InfoKind infoKind) {
        this.variable = variable;
    }
    
    public RubyVariable variable() {
        return variable;
    }
    
    public void accept(RubyConstruct construct) {
        if (construct instanceof VariableAffector)
            ((VariableAffector) construct).actOnVariable(this);
    }
    
    public AssignmentInfo[] assigned() {
        return assigned.toArray(new AssignmentInfo[assigned.size()]);
    }
    
    public void accept(AssignmentInfo info) {
        if (matches(info.threesome()))
            add(info);
    }
    
    private boolean matches(ThingAccessInfo threesome) {
        return variable.name().equals(threesome.variableName());
    }
    
    private void add(AssignmentInfo info) {
        assigned.add(info);
    }
    
    public boolean done() {
        return false;
    }
    
    public void visit(RubyConstruct construct) {
        accept(construct);
    }
    
}
