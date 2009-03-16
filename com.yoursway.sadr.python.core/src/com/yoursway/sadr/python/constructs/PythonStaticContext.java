package com.yoursway.sadr.python.constructs;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.StaticContext;
import com.yoursway.sadr.engine.incremental.index.Index;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;

public interface PythonStaticContext extends PythonConstruct,
        StaticContext<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> {
    
    PythonStaticContext parentScope();
    
    String name();
    
    PythonFileC getFileScope();
    
    Index getIndex();
    
}
