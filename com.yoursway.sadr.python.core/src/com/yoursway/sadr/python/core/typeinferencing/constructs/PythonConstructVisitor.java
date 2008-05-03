package com.yoursway.sadr.python.core.typeinferencing.constructs;

//TODO extract interface.

public class PythonConstructVisitor {
    public boolean trap(PythonConstruct construct) {
        return true;
    }
    
    public boolean visit(PythonConstruct construct) {
        return trap(construct);
    }
    
    void endVisit(PythonConstruct construct) {
    }
    
    public boolean visit(ReturnC construct) {
        return true;
    }
    
    void endVisit(ReturnC construct) {
    }
}
