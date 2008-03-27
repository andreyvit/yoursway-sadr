package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.ruby.core.runtime.std.StandardTypes;

public abstract class AbstractScope implements Scope {
    
    @Override
    public abstract String toString();
    
    public StandardTypes builtins() {
        return classLookup().standardTypes();
    }
    
    public Collection<ModuleDeclaration> extentionsOf(ASTNode node) {
        return nodeLookup().extentionsOf(node);
    }
    
    public Scope nearestScope() {
        return this;
    }
    
}
