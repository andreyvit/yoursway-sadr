package com.yoursway.sadr.python.analysis.context.lexical.scopes;


public final class ModuleScope extends PythonScope {
    
    @Override
    public ModuleScope getFileScope() {
        return this;
    }
    
    @Override
    public boolean isGlobalScope() {
        return true;
    }
    
    @Override
    public PythonScope parentScope() {
        return null;
    }
    
}
