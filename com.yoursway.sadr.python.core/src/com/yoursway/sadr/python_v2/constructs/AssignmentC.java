package com.yoursway.sadr.python_v2.constructs;

import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;
import static java.util.Collections.singletonList;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.AssignmentInfoRequestor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexRequest;
import com.yoursway.sadr.python.core.typeinferencing.goals.AssignmentInfo;
import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGoal;

public class AssignmentC extends PythonConstructImpl<Assignment> {
    
    private final PythonConstruct leftPart;
    private final PythonConstruct rightPart;
    
    AssignmentC(Scope scope, Assignment node) {
        super(scope, node);
        Assert.isLegal(node.getLeft() != null, "node.getLeft() should be != null");
        Assert.isLegal(node.getRight() != null, "node.getRight() should be != null");
        
        leftPart = wrap(node.getLeft());
        rightPart = wrap(node.getRight());
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
    public PythonConstruct lhs() {
        return leftPart;
    }
    
    public PythonConstruct rhs() {
        return rightPart;
    }
    
    public void actOnVariable(AssignmentInfoRequestor request) {
        provideAssignmentInfo(request);
    }
    
    public void actOnIndex(IndexRequest request) {
        provideAssignmentInfo(request);
    }
    
    @Override
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        return rightPart.evaluate(context, acceptor);
    }
    
    private void provideAssignmentInfo(AssignmentInfoRequestor request) {
        PythonConstruct lhs = lhs();
        PythonConstruct rhs = rhs();
        Collection<MumblaWumblaThreesome> swingerParty = lhs.mumblaWumbla();
        for (MumblaWumblaThreesome threesome : swingerParty)
            request.accept(new AssignmentInfo(threesome, rhs));
    }
    
    public String getName() {
        Statement left = node.getLeft();
        if (left instanceof VariableReference) {
            VariableReference reference = (VariableReference) left;
            return reference.getName();
        }
        return null;
    }
    
    @Override
    public Effects getEffects() {
        Effect effect = new AssignmentEffect(lhs().toFrog(), rhs().toFrog());
        return new Effects(singletonList(effect), Effects.NO_FROGS);
    }
    
    //    public void actOnModel(ModelRequest request) {
    //        PythonConstruct lhs = lhs();
    //        Collection<MumblaWumblaThreesome> swingerParty = lhs.mumblaWumbla();
    //        for (MumblaWumblaThreesome threesome : swingerParty)
    //            if (threesome.receiver() == null) {
    //                String name = threesome.variableName();
    //                staticContext().variableLookup().lookupVariable(name);
    //            }
    //        
    //        //        Statement left = node.getLeft();
    //        //        if (left instanceof ExtendedVariableReference) {
    //        //            ExtendedVariableReference evr = (ExtendedVariableReference) left;
    //        //            List<ASTNode> children = PythonUtils.expressionsOf(evr);
    //        //            if (children.size() == 2 && children.get(0) instanceof VariableReference
    //        //                    && ((VariableReference) children.get(0)).getName().equals("self")
    //        //                    && children.get(1) instanceof VariableReference) {
    //        //                VariableReference fieldName = (VariableReference) children.get(1);
    //        //                PythonClassImpl klass = staticContext().currentClass();
    //        //                new PythonSourceField(request.context(), klass, fieldName.getName(), fieldName);
    //        //            }
    //        //        }
    //    }
    
}
