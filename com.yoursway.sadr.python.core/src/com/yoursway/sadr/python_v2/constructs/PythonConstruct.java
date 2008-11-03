package com.yoursway.sadr.python_v2.constructs;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.PythonStatement;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGoal;

public interface PythonConstruct {
    
    public ASTNode node();
    
    IGoal evaluate(Context context, PythonValueSetAcceptor acceptor);
    
    IGoal evaluate(Context context, PythonVariableAcceptor acceptor);
    
    Scope parentScope();
    
    Scope scope();
    
    List<PythonConstruct> getPostChildren();
    
    List<PythonConstruct> getPreChildren();
    
    PythonConstruct getSyntacticallyPreviousConstruct();
    
    PythonConstruct parent();
    
    List<PythonStatement> getStatements();
}
