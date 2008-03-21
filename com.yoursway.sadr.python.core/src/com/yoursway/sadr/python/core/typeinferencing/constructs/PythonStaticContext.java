package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.core.constructs.StaticContext;
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.python.core.typeinferencing.services.NodeLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.ProcedureLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.SearchService;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public interface PythonStaticContext extends
        StaticContext<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> {
    
    StandardTypes builtins();
    
    ClassLookup classLookup();
    
    ProcedureLookup procedureLookup();
    
    Collection<ModuleDeclaration> extentionsOf(ASTNode node);
    
    PythonClass currentClass();
    
    ValueInfo selfType();
    
    VariableLookup variableLookup();
    
    InstanceRegistrar instanceRegistrar();
    
    SearchService searchService();
    
    NodeLookup nodeLookup();
    
    FileScope fileScope();
    
    PythonConstruct createConstruct();
    
    Scope nearestScope();
    
}
