package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.Assignment;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.DotFrog;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Index;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.croco.PythonRecord;
import com.yoursway.sadr.python_v2.goals.Acceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.succeeder.IGoal;

public class AssignmentC extends PythonConstructImpl<Assignment> implements PythonDeclaration {
    
    private final PythonConstruct leftPart;
    private final PythonConstruct rightPart;
    private final int LEFT = 0, RIGHT = 1;
    
    AssignmentC(Scope scope, Assignment node) {
        super(scope, node);
        if (node.getLeft() == null)
            throw new IllegalArgumentException("node.getLeft() should be not null");
        if (node.getRight() == null)
            throw new IllegalArgumentException("node.getRight() should be not null");
        
        leftPart = getPostChildren().get(LEFT);
        rightPart = getPostChildren().get(RIGHT);
    }
    
    public PythonConstruct lhs() {
        return leftPart;
    }
    
    public PythonConstruct rhs() {
        return rightPart;
    }
    
    public boolean match(Frog frog) {
        return frog.match(name());
    }
    
    @Override
    public IGoal evaluate(Krocodile context, PythonValueSetAcceptor acceptor) {
        return rightPart.evaluate(context, acceptor);
    }
    
    public void index(Krocodile crocodile, final Acceptor acceptor) {
        if (leftPart instanceof VariableReferenceC) {
            String name = ((VariableReferenceC) leftPart).name();
            Index.newRecord(name, crocodile, this);
        } else if (leftPart instanceof FieldAccessC) {
            FieldAccessC varRead = (FieldAccessC) leftPart;
            String tail = varRead.variable().name();
            if (searchFrog.match(tail)) {
                PythonRecord head = lookup(dotFrog.head(), varRead.receiver());
                if (head == null) {
                    return null;
                }
                DotFrog resultFrog = new DotFrog(dotFrog.head(), tail, Frog.UNKNOWN);
                PythonRecord whole = Index.lookup(resultFrog);
            }
        }
        acceptor.subgoalDone(Grade.DONE);
    }
    
    public String name() {
        if (leftPart instanceof VariableReferenceC) {
            return ((VariableReferenceC) leftPart).name();
        }
        return null;
    }
}
