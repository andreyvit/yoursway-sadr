package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;

public interface CodeGatherer {
    
    void add(FileScope fileScope, ModuleDeclaration rootNode, ASTNode fakeParent);
    
}