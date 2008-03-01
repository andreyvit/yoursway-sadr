package com.yoursway.sadr.ruby.core.typeinferencing.services;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.ruby.core.runtime.contributions.NodeBoundItem;

public interface OuteriorNodeLookup {
    
    NodeBoundItem lookup(ISourceModule file, ASTNode node);
    
}
