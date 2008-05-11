package com.yoursway.sadr.python_v2.goals;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.succeeder.Goal;

public class ResolveReference extends Goal {
    
    private final PythonValueSetAcceptor acceptor;
    
    public ResolveReference(PythonVariableAccessExpression expression, PythonValueSetAcceptor acceptor) {
        this.acceptor = acceptor;
    }
    
    public void preRun() {
        // TODO Auto-generated method stub
        acceptor.checkpoint(Grade.DONE);
    }
}
