package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.core.constructs.StaticContext;
import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.runtime.std.StandardTypes;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ProcedureLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.VariableLookup;

public interface RubyStaticContext extends
        StaticContext<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> {
    
    ASTNode parentNodeOf(ASTNode node);
    
    RubyStaticContext subcontextFor(ASTNode node);
    
    RubyClass currentClass();
    
    ValueInfo selfType();
    
    VariableLookup variableLookup();
    
    StandardTypes builtins();
    
    ClassLookup classLookup();
    
    ProcedureLookup procedureLookup();
    
    Collection<ModuleDeclaration> extentionsOf(ASTNode node);
    
    Scope nearestScope();
    
}
