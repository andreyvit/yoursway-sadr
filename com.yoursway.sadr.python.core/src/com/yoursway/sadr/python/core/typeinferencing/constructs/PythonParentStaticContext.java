package com.yoursway.sadr.python.core.typeinferencing.constructs;

public class PythonParentStaticContext extends PythonDelegatingStaticContext {
    
    private final PythonConstruct parentConstruct;
    
    public PythonParentStaticContext(PythonStaticContext parentContext, PythonConstruct parentConstruct) {
        super(parentContext);
        this.parentConstruct = parentConstruct;
    }
    
    @Override
    public PythonConstruct parentConstruct() {
        return parentConstruct;
    }
    
}
