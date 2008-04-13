package com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.VisitorRequestor;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.AccessInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.AssignmentInfo;

public class VariableRequest implements
        Request<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode>, AssignmentInfoRequestor,
        AssignmentInfoProvider {
    
    private final RubyVariable variable;
    
    private final Collection<AssignmentInfo> assigned = new ArrayList<AssignmentInfo>();
    
    public VariableRequest(RubyVariable variable, InfoKind infoKind) {
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
    
    public ContinuationRequestorCalledToken enter(RubyConstruct construct, ContinuationScheduler requestor,
            VisitorRequestor<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> continuation) {
        accept(construct);
        return continuation.consume(this, requestor);
    }
    
    public void leave() {
    }
    
    public void accept(AssignmentInfo info) {
        if (matches(info.accessInfo()))
            add(info);
    }
    
    private boolean matches(AccessInfo access) {
        return variable.name().equals(access.variableName());
    }
    
    private void add(AssignmentInfo info) {
        assigned.add(info);
    }
}
