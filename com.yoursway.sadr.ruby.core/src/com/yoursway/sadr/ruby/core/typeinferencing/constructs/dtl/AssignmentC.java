package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ruby.ast.RubyAssignment;
import org.eclipse.dltk.ruby.ast.RubyColonExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl.rq.VariableAffector;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl.rq.VariableRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.AssignmentInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.Wildcard;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public class AssignmentC extends DtlConstruct<RubyAssignment> implements VariableAffector {
    
    AssignmentC(RubyStaticContext sc, RubyAssignment node) {
        super(sc, node);
    }
    
    public void evaluateValue(RubyDynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        continuation.consume(emptyValueInfo(), requestor);
    }
    
    public void actOnVariable(VariableRequest subject) {
        ASTNode lhs = node.getLeft();
        ASTNode rhs = node.getRight();
        if (rhs != null) {
            ASTNode terminal = RubyUtils.assignmentTerminalNode(lhs);
            Wildcard wildcard = RubyUtils.assignmentWildcardExpression(lhs);
            if (matches(subject, terminal)) {
                Scope subscope = RubyUtils.restoreSubscope((Scope) rubyStaticContext(), rhs);
                RubyConstruct construct = new RubyConstruct(subscope, rhs);
                AssignmentInfo info = new AssignmentInfo(wildcard, construct);
                subject.add(info);
            }
        }
        
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
    
}
