package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

public interface ConstructVisitor {
    
    ConstructVisitor enter(IConstruct construct);
    
    void leave();
    
}
