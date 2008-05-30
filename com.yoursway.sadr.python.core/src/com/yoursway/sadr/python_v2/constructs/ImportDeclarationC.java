package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.python_v2.model.builtins.ModuleValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public interface ImportDeclarationC extends PythonConstruct {
    boolean hasImport(String variable);
    
    PythonConstruct resolveImport(PythonValue<ModuleValue> value, String path) throws Exception;
    
    PythonValue<ModuleValue> resolveAlias(String alias);
    
    PythonFileC resolvePath(String path);
}
