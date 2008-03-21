package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.core.constructs.StaticContext;
import com.yoursway.sadr.ruby.core.runtime.std.StandardTypes;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ProcedureLookup;

public interface RubyStaticContext extends
        StaticContext<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> {
    
    ASTNode parentNodeOf(ASTNode node);
    
    RubyStaticContext subcontextFor(ASTNode node);
    
    StandardTypes builtins();
    
    ClassLookup classLookup();
    
    ProcedureLookup procedureLookup();
    
    Collection<ModuleDeclaration> extentionsOf(ASTNode node);
    
}
