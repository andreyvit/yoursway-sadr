package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.BinaryExpressionGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGoal;

public abstract class BinaryC extends PythonConstructImpl<BinaryExpression> {
    
    BinaryC(Scope sc, BinaryExpression node) {
        super(sc, node);
        assert node.getChilds().size() == 2;
    }
    
    public PythonConstruct getLeft() {
        return getChildConstructs().get(0);
    }
    
    public PythonConstruct getRight() {
        return getChildConstructs().get(1);
    }
    
    public abstract String getOperationMethodName();
    
    @Override
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        return new BinaryExpressionGoal(this, context, acceptor);
    }
}
