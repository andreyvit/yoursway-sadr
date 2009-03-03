package com.yoursway.sadr.python_v2.constructs;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.ContextSensitiveGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public interface PythonConstruct {
    
    ASTNode node();
    
    ContextSensitiveGoal execute(Krocodile crocodile);
    
    PythonValueSet evaluate(Krocodile crocodile);
    
    Scope parentScope();
    
    Scope scope();
    
    List<PythonConstruct> getPostChildren();
    
    List<PythonConstruct> getPreChildren();
    
    PythonConstruct getSyntacticallyPreviousConstruct();
}
