package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.IRootConstruct;

public interface RootPythonConstruct extends
        IRootConstruct<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode>, PythonConstruct {
    
}
