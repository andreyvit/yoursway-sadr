package com.yoursway.sadr.python_v2.constructs;

import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGoal;

public interface PythonConstruct {
    
    Collection<MumblaWumblaThreesome> mumblaWumbla();
    
    public ASTNode node();
    
    IGoal evaluate(Context context, PythonValueSetAcceptor acceptor);
    
    Scope parentScope();
    
    Scope innerScope();
    
    List<PythonConstruct> getPostChildren();
    
    Frog toFrog();
    
    Effects getEffects();
    
    List<PythonConstruct> getPreChildren();
    
    PythonConstruct getSyntacticallyPreviousConstruct();
    
    PythonConstruct parent();
}
