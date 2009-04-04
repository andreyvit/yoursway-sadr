package com.yoursway.sadr.python.analysis.context.lexical.scopes;

import com.yoursway.sadr.engine.incremental.SourceUnit;

public final class ModuleScope extends PythonScope {
    
    private final SourceUnit sourceUnit;
    
    public ModuleScope(SourceUnit sourceUnit) {
        if (sourceUnit == null)
            throw new NullPointerException("sourceUnit is null");
        this.sourceUnit = sourceUnit;
    }
    
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
    
    public SourceUnit getSourceUnit() {
        return sourceUnit;
    }
    
    @Override
    protected boolean containsOffset(int offset) {
        return true;
    }
    
    @Override
    public String toString() {
        return sourceUnit.toString();
    }
    
}
