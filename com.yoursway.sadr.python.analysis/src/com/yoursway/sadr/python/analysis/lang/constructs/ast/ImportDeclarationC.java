package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.objectmodel.values.ModuleValue;

public interface ImportDeclarationC extends PythonConstruct {
    boolean hasImport(String variable);
    
    PythonConstruct resolveImport(ModuleValue value, String path) throws Exception;
    
    ModuleValue resolveAlias(String name);
    
    PythonFileC resolvePath(String path);
}
