package com.yoursway.sadr.python.analysis.context.lexical.scopes;

import com.yoursway.sadr.python.analysis.Range;

public class ClassScope extends InnerScope {
    
    private final String name;
    
    public ClassScope(PythonScope parentScope, String name, Range range) {
        super(parentScope, range);
        if (range == null)
            throw new NullPointerException("range is null");
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "!" + name;
    }
    
}
