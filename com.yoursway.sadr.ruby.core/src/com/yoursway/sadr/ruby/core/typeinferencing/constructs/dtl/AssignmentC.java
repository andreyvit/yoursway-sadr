package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ruby.ast.RubyAssignment;
import org.eclipse.dltk.ruby.ast.RubyColonExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.AssignmentInfoRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.IndexAffector;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.IndexRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.ModelAffector;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.ModelRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.VariableAffector;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.VariableRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.AssignmentInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ThingAccessInfo;

public class AssignmentC extends DtlConstruct<RubyAssignment> implements VariableAffector, IndexAffector,
        ModelAffector {
    
    AssignmentC(RubyStaticContext sc, RubyAssignment node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(RubyDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
    public RubyConstruct lhs() {
        return wrap(innerContext(), node.getLeft());
    }
    
    public RubyConstruct rhs() {
        return wrap(innerContext(), node.getRight());
    }
    
    public void actOnVariable(AssignmentInfoRequestor request) {
        provideAssignmentInfo(request);
    }
    
    public void actOnIndex(IndexRequest request) {
        provideAssignmentInfo(request);
    }
    
    private void provideAssignmentInfo(AssignmentInfoRequestor request) {
        RubyConstruct lhs = lhs();
        RubyConstruct rhs = rhs();
        Collection<ThingAccessInfo> accessInfos = lhs.accessInfos();
        for (ThingAccessInfo access : accessInfos)
            request.accept(new AssignmentInfo(access, rhs));
    }
    
    protected boolean matches(VariableRequest request, ASTNode terminal) {
        boolean doit = false;
        if (terminal instanceof SimpleReference) {
            doit = (((SimpleReference) terminal).getName().equalsIgnoreCase(request.variable().name()));
        } else if (terminal instanceof RubyColonExpression) {
            ; // TODO
        }
        return doit;
    }
    
    public void actOnModel(ModelRequest request) {
        RubyConstruct lhs = lhs();
        Collection<ThingAccessInfo> accessInfos = lhs.accessInfos();
        for (ThingAccessInfo access : accessInfos)
            if (access.receiver() == null) {
                String name = access.variableName();
                staticContext().variableLookup().lookupVariable(name);
            }
    }
    
}
