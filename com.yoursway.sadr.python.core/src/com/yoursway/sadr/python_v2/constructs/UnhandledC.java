package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class UnhandledC extends PythonConstructImpl<ASTNode> {
    
    UnhandledC(Scope sc, ASTNode node) {
        super(sc, node);
    }
    
}
