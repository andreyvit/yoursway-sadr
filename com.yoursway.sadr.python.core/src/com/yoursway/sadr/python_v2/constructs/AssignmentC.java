package com.yoursway.sadr.python_v2.constructs;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;

import com.google.common.collect.Lists;
import com.yoursway.sadr.python.PythonStatement;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGoal;

public class AssignmentC extends PythonConstructImpl<Assignment> implements PythonStatement {
    
    private final PythonConstruct leftPart;
    private final PythonConstruct rightPart;
    private final int LEFT = 0, RIGHT = 1;
    
    AssignmentC(Scope scope, Assignment node) {
        super(scope, node);
        if (node.getRight() == null) {
            System.out.println("Weird construct");
        }
        Assert.isLegal(node.getLeft() != null, "node.getLeft() should be != null");
        Assert.isLegal(node.getRight() != null, "node.getRight() should be != null");
        
        leftPart = getPostChildren().get(LEFT);
        rightPart = getPostChildren().get(RIGHT);
    }
    
    public PythonConstruct lhs() {
        return leftPart;
    }
    
    public PythonConstruct rhs() {
        return rightPart;
    }
    
    @Override
    public boolean match(Frog frog) {
        PythonConstruct lhs = this.lhs();
        if (lhs instanceof VariableReferenceC) {
            return lhs.match(frog);
        }
        return false;
    }
    
    @Override
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        return rightPart.evaluate(context, acceptor);
    }
    
    @Override
    public IGoal evaluate(Context context, final PythonVariableAcceptor acceptor) {
        if (this.lhs() instanceof VariableReferenceC) {
            final VariableReferenceC reference = (VariableReferenceC) this.lhs();
            final String name = reference.name();
            return evaluate(context, new PythonValueSetDelegatingAcceptor(context, name, acceptor));
        } else {
            return null;
        }
    }
    
    @Override
    public List<PythonStatement> getStatements() {
        return Lists.newArrayList((PythonStatement) this);
    }
    
    public String getName() {
        Statement left = node.getLeft();
        if (left instanceof VariableReference) {
            VariableReference reference = (VariableReference) left;
            return reference.getName();
        }
        return null;
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
