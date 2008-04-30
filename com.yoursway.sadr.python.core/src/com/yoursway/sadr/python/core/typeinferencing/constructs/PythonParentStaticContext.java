package com.yoursway.sadr.python.core.typeinferencing.constructs;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class PythonParentStaticContext extends PythonDelegatingStaticContext {
    
    private final PythonConstruct parentConstruct;
    
    public PythonParentStaticContext(Scope parentContext, PythonConstruct parentConstruct) {
        super(parentContext);
        this.parentConstruct = parentConstruct;
    }
    
    @Override
    public PythonConstruct parentConstruct() {
        return parentConstruct;
    }
    
}
