package com.yoursway.sadr.python.analysis.context.lexical.scopes;


public abstract class InnerScope extends PythonScope {
    
    private final PythonScope parentScope;
    
    public InnerScope(PythonScope parentScope) {
        if (parentScope == null)
            throw new NullPointerException("parentScope is null");
        this.parentScope = parentScope;
    }
    
    @Override
    public final PythonScope parentScope() {
        return parentScope;
    }
    
    @Override
    public final ModuleScope getFileScope() {
        return parentScope.getFileScope();
    }
    
    @Override
    public final boolean isGlobalScope() {
        return false;
    }
    
}
