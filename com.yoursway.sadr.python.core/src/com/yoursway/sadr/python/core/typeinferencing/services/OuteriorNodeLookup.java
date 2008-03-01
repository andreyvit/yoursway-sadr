package com.yoursway.sadr.python.core.typeinferencing.services;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.python.core.runtime.contributions.NodeBoundItem;

public interface OuteriorNodeLookup {
    
    NodeBoundItem lookup(ISourceModule file, ASTNode node);
    
}
