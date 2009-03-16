package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.python_v2.model.builtins.values.ModuleValue;

public interface ImportDeclarationC extends PythonConstruct {
    boolean hasImport(String variable);
    
    PythonConstruct resolveImport(ModuleValue value, String path) throws Exception;
    
    ModuleValue resolveAlias(String name);
    
    PythonFileC resolvePath(String path);
}
