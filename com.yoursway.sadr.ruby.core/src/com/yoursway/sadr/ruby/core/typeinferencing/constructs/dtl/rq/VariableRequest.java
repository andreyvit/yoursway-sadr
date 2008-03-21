package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl.rq;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.VisitorRequestor;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.AssignmentInfoProvider;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.AssignmentInfoRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.AssignmentInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ThingAccessInfo;

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
    
    public void accept(RubyConstruct rubyConstruct) {
        if (rubyConstruct instanceof VariableAffector)
            ((VariableAffector) rubyConstruct).actOnVariable(this);
    }
    
    public void add(AssignmentInfo info) {
        assigned.add(info);
    }
    
    public AssignmentInfo[] assigned() {
        return assigned.toArray(new AssignmentInfo[assigned.size()]);
    }
    
    public void enter(RubyConstruct construct, ContinuationRequestor requestor,
            VisitorRequestor<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> continuation) {
        accept(construct);
        continuation.consume(this, requestor);
    }
    
    public void leave() {
        
    }
    
    public void accept(AssignmentInfo info) {
        if (matches(info.threesome()))
            add(info);
    }
    
    private boolean matches(ThingAccessInfo access) {
        return variable.name().equals(access.variableName());
    }
    
}
