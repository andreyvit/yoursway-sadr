package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.IRootConstruct;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public interface RootPythonConstruct extends
        IRootConstruct<PythonConstruct, Scope, PythonDynamicContext, ASTNode>, PythonConstruct {
    
}
