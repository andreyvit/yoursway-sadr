package com.yoursway.sadr.python.analysis.context.lexical.scopes;

import com.yoursway.sadr.python.analysis.Range;

public class LambdaScope extends InnerScope {
    
    public LambdaScope(PythonScope parentScope, Range range) {
        super(parentScope, range);
    }
    
    @Override
    public String toString() {
        return "<lambda>";
    }
    
}
