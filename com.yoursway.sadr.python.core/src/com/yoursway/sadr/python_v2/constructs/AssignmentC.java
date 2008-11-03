package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.succeeder.IGoal;

public class AssignmentC extends PythonConstructImpl<Assignment> {
    
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
            //this is here because VRC.match should always return false
            return frog.match(((VariableReferenceC) lhs).name());
        }
        //FIXME: return false for invalid constructs
        return lhs.match(frog);
    }
    
    @Override
    public IGoal evaluate(Krocodile context, PythonValueSetAcceptor acceptor) {
        return rightPart.evaluate(context, acceptor);
    }
    
    @Override
    public IGoal evaluate(Krocodile context, final PythonVariableAcceptor acceptor) {
        if (this.lhs() instanceof VariableReferenceC) {
            final VariableReferenceC reference = (VariableReferenceC) this.lhs();
            final String name = reference.name();
            return evaluate(context, new PythonValueSetDelegatingAcceptor(context, name, acceptor));
        } else {
            return null;
        }
    }
    
    public String getName() {
        Statement left = node.getLeft();
        if (left instanceof VariableReference) {
            VariableReference reference = (VariableReference) left;
            return reference.getName();
        }
        return null;
    }
}
