package com.yoursway.sadr.python.constructs;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.StaticContext;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;

public interface PythonStaticContext extends PythonConstruct,
        StaticContext<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode>, PythonScope {
    
    String name();
    
    PythonFileC getFileScope();
    
    MethodDeclarationC getParentMethodDeclarationC();
    
    void addGlobalVariable(String name);
    
    void addLocalVariable(String name);
    
}
