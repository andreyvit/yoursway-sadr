package com.yoursway.sadr.python.core.typeinferencing.constructs;

public interface ConstructVisitor {
    
    ConstructVisitor enter(IConstruct construct);
    
    void leave();
    
}
