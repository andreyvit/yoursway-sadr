package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;
import org.eclipse.dltk.python.parser.ast.expressions.ExtendedVariableReference;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.RubyClass;
import com.yoursway.sadr.python.core.runtime.RubySourceField;
import com.yoursway.sadr.python.core.runtime.RubyUtils;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.IndexAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.IndexRequest;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.ModelAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.ModelRequest;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.VariableAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.VariableRequest;
import com.yoursway.sadr.python.core.typeinferencing.goals.AssignmentInfo;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.Wildcard;

public class AssignmentC extends PythonConstructImpl<Assignment> implements VariableAffector, IndexAffector,
        ModelAffector {
    
    AssignmentC(PythonStaticContext sc, Assignment node) {
        super(sc, node);
    }
    
    public void evaluateValue(PythonDynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        continuation.consume(emptyValueInfo(), requestor);
    }
    
    public void actOnVariable(VariableRequest subject) {
        ASTNode lhs = node.getLeft();
        ASTNode rhs = node.getRight();
        if (rhs != null) {
            ASTNode terminal = RubyUtils.assignmentTerminalNode(lhs);
            Wildcard wildcard = RubyUtils.assignmentWildcardExpression(lhs);
            if (matches(subject, terminal))
                subject.add(new AssignmentInfo(wildcard, subconstructFor(rhs)));
        }
        
    }
    
    protected boolean matches(VariableRequest request, ASTNode terminal) {
        boolean doit = false;
        if (terminal instanceof VariableReference) {
            doit = (((VariableReference) terminal).getName().equalsIgnoreCase(request.variable().name()));
        } else {
            //if (terminal instanceof RubyColonExpression) {
            ; // TODO
        }
        return doit;
    }
    
    public void actOnIndex(IndexRequest request) {
        ASTNode lhs = node.getLeft();
        ASTNode rhs = node.getRight();
        if (rhs != null) {
            ASTNode terminal = RubyUtils.assignmentTerminalNode(lhs);
            Wildcard wildcard = RubyUtils.assignmentWildcardExpression(lhs);
            if (terminal instanceof VariableReference)
                request.addAssignment(((VariableReference) terminal).getName(), new AssignmentInfo(wildcard,
                        subconstructFor(rhs)));
        }
    }
    
    public void actOnModel(ModelRequest request) {
        Statement left = node.getLeft();
        if (left instanceof ExtendedVariableReference) {
            ExtendedVariableReference evr = (ExtendedVariableReference) left;
            List<ASTNode> children = RubyUtils.expressionsOf(evr);
            if (children.size() == 2 && children.get(0) instanceof VariableReference
                    && ((VariableReference) children.get(0)).getName().equals("self")
                    && children.get(1) instanceof VariableReference) {
                VariableReference fieldName = (VariableReference) children.get(1);
                RubyClass klass = staticContext().currentClass();
                new RubySourceField(request.context(), klass, fieldName.getName(), fieldName);
            }
        }
    }
    
}
