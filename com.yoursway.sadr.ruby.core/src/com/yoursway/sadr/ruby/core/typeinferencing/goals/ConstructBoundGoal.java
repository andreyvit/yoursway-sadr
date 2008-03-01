package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.ruby.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public interface ConstructBoundGoal {
    
    Construct<Scope, ASTNode> construct();
    
}
