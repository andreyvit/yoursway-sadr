package com.yoursway.sadr.python.analysis.context.lexical;

import java.util.List;

public interface PythonScope {
    
    PythonScope parentScope();
    
    List<PythonScope> currentScopes();
    
    PythonStaticContext scopeContext();
    
    boolean isGlobalVariable(String name);
    
    boolean isGlobalScope();
    
    boolean isLocalVariable(String name);
    
    PythonScope findDefiningScope(String name);
    
    void addLocalVariable(String name);
    
}
