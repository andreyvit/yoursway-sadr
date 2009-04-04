package com.yoursway.sadr.python.analysis.context.lexical.scopes;

import com.yoursway.sadr.python.analysis.Range;

public abstract class InnerScope extends PythonScope {
    
    private final PythonScope parentScope;
    private final Range range;
    
    public InnerScope(PythonScope parentScope, Range range) {
        if (parentScope == null)
            throw new NullPointerException("parentScope is null");
        if (range == null)
            throw new NullPointerException("range is null");
        this.parentScope = parentScope;
        parentScope.innerScopes.add(this);
        this.range = range;
    }
    
    @Override
    protected boolean containsOffset(int offset) {
        return range.contains(offset);
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
