package com.yoursway.sadr.python.core.typeinferencing.goals;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public interface ConstructBoundGoal {
    
    Construct<Scope, ASTNode> construct();
    
}
