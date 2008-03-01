package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.runtime.std.StandardTypes;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.StaticContext;

public abstract class AbstractScope implements Scope {
    
    @Override
    public abstract String toString();
    
    public ASTNode parentNodeOf(ASTNode node) {
        return nodeLookup().parentOf(node);
    }
    
    public StaticContext subcontextFor(ASTNode node) {
        return RubyUtils.restoreSubscope(this, node);
    }
    
    public StandardTypes builtins() {
        return classLookup().standardTypes();
    }
    
    public Collection<ModuleDeclaration> extentionsOf(ASTNode node) {
        return nodeLookup().extentionsOf(node);
    }
    
}
