package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.AssignmentInfoRequestor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexRequest;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.ModelAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.ModelRequest;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.VariableAffector;
import com.yoursway.sadr.python.core.typeinferencing.goals.AssignmentInfo;
import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;

public class AssignmentC extends PythonConstructImpl<Assignment> implements VariableAffector, IndexAffector,
        ModelAffector {
    
    AssignmentC(PythonStaticContext sc, Assignment node) {
        super(sc, node);
        Assert.isLegal(node.getLeft() != null, "node.getLeft() should be != null");
        Assert.isLegal(node.getRight() != null, "node.getRight() should be != null");
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
    public PythonConstruct lhs() {
        return wrap(innerContext(), node.getLeft());
    }
    
    public PythonConstruct rhs() {
        return wrap(innerContext(), node.getRight());
    }
    
    public void actOnVariable(AssignmentInfoRequestor request) {
        provideAssignmentInfo(request);
    }
    
    public void actOnIndex(IndexRequest request) {
        provideAssignmentInfo(request);
    }
    
    private void provideAssignmentInfo(AssignmentInfoRequestor request) {
        PythonConstruct lhs = lhs();
        PythonConstruct rhs = rhs();
        Collection<MumblaWumblaThreesome> swingerParty = lhs.mumblaWumbla();
        for (MumblaWumblaThreesome threesome : swingerParty)
            request.accept(new AssignmentInfo(threesome, rhs));
    }
    
    public void actOnModel(ModelRequest request) {
        PythonConstruct lhs = lhs();
        Collection<MumblaWumblaThreesome> swingerParty = lhs.mumblaWumbla();
        for (MumblaWumblaThreesome threesome : swingerParty)
            if (threesome.receiver() == null) {
                String name = threesome.variableName();
                staticContext().variableLookup().lookupVariable(name);
            }
        
        //        Statement left = node.getLeft();
        //        if (left instanceof ExtendedVariableReference) {
        //            ExtendedVariableReference evr = (ExtendedVariableReference) left;
        //            List<ASTNode> children = PythonUtils.expressionsOf(evr);
        //            if (children.size() == 2 && children.get(0) instanceof VariableReference
        //                    && ((VariableReference) children.get(0)).getName().equals("self")
        //                    && children.get(1) instanceof VariableReference) {
        //                VariableReference fieldName = (VariableReference) children.get(1);
        //                PythonClass klass = staticContext().currentClass();
        //                new PythonSourceField(request.context(), klass, fieldName.getName(), fieldName);
        //            }
        //        }
    }
    
}
