package com.yoursway.sadr.python.constructs;

import com.yoursway.sadr.python.model.values.ModuleValue;

public interface ImportDeclarationC extends PythonConstruct {
    boolean hasImport(String variable);
    
    PythonConstruct resolveImport(ModuleValue value, String path) throws Exception;
    
    ModuleValue resolveAlias(String name);
    
    PythonFileC resolvePath(String path);
}
