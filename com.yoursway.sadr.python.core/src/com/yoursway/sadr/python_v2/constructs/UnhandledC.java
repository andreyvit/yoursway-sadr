package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.PassResultGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.succeeder.IGoal;

public class UnhandledC extends PythonConstructImpl<ASTNode> {
    
    UnhandledC(Scope sc, ASTNode node) {
        super(sc, node);
    }
    
    @Override
    public IGoal evaluate(Krocodile context, PythonValueSetAcceptor acceptor) {
        return new PassResultGoal(context, acceptor, null);
    }
}
