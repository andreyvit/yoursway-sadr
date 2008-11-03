package com.yoursway.sadr.python_v2.constructs;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.succeeder.IGoal;

public interface PythonConstruct {
    
    public ASTNode node();
    
    IGoal evaluate(Krocodile crocodile, PythonValueSetAcceptor acceptor);
    
    IGoal evaluate(Krocodile crocodile, PythonVariableAcceptor acceptor);
    
    Scope parentScope();
    
    Scope scope();
    
    List<PythonConstruct> getPostChildren();
    
    List<PythonConstruct> getPreChildren();
    
    PythonConstruct getSyntacticallyPreviousConstruct();
    
    public boolean match(Frog frog);// true if LHS matches this frog, for definitions only
}
