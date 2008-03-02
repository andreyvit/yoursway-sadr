package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.core.constructs.StaticContext;
import com.yoursway.sadr.python.core.runtime.RubyClass;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.ProcedureLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public interface PythonStaticContext extends
        StaticContext<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> {
    
    StandardTypes builtins();
    
    ClassLookup classLookup();
    
    ProcedureLookup procedureLookup();
    
    Collection<ModuleDeclaration> extentionsOf(ASTNode node);
    
    RubyClass currentClass();
    
    ValueInfo selfType();
    
    VariableLookup variableLookup();
    
}
