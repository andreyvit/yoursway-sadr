package com.yoursway.sadr.ruby.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.ruby.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public interface Callable {
    
    boolean isBuiltin();
    
    String name();
    
    RubyArgument[] arguments();
    
    Construct<Scope, ASTNode> construct();
    
    String[] parameterNames();
    
    String qualifiedName();
    
}
