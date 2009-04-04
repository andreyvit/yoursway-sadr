package com.yoursway.sadr.python.analysis.context.lexical;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.StaticContext;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.MethodDeclarationC;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.PythonFileC;

public interface PythonStaticContext extends PythonConstruct,
        StaticContext<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode>, PythonScope {
    
    String name();
    
    PythonFileC getFileScope();
    
    MethodDeclarationC getParentMethodDeclarationC();
    
    void addGlobalVariable(String name);
    
    void addLocalVariable(String name);
    
    List<PythonScope> currentScopesIncludingSelf();
    
}
