package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;

public interface PythonConstruct {
    
    Collection<MumblaWumblaThreesome> mumblaWumbla();
    
    void traverse(PythonConstructVisitor visitor);
    
    public ASTNode node();
    
}
