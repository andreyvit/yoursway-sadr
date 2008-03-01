package com.yoursway.sadr.python.core.typeinferencing.services;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.python.core.runtime.contributions.NodeBoundItem;

public interface NodeLookup {
    
    NodeBoundItem lookup(ASTNode node);
    
    ASTNode parentOf(ASTNode node);
    
    Collection<ModuleDeclaration> extentionsOf(ASTNode node);
    
}
