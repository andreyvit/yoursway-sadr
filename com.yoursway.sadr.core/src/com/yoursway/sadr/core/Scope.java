package com.yoursway.sadr.core;

import org.eclipse.dltk.ast.ASTNode;

public abstract class Scope {
    
    public abstract Construct wrap(ASTNode child);
    
}
