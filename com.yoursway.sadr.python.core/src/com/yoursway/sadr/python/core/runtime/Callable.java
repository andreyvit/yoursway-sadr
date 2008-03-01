package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public interface Callable {
    
    boolean isBuiltin();
    
    String name();
    
    RubyArgument[] arguments();
    
    Construct<Scope, ASTNode> construct();
    
    String[] parameterNames();
    
    String qualifiedName();
    
}
