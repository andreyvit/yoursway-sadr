package com.yoursway.sadr.ruby.core.typeinferencing.services;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.ruby.core.runtime.contributions.NodeBoundItem;

public interface NodeLookup {
    
    NodeBoundItem lookup(ASTNode node);
    
    Collection<ModuleDeclaration> extentionsOf(ASTNode node);
    
}
