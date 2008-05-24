package com.yoursway.sadr.python_v2.constructs;

public abstract class PythonConstructVisitor {
    /**
     * Trap function is called on each construct visiting by default.
     * 
     * @param construct
     * @return <code>true</code>
     */
    public boolean trap(PythonConstruct construct) {
        return true;
    }
    
    public boolean visit(PythonConstruct construct) {
        return trap(construct);
    }
    
    void endVisit(PythonConstruct construct) {
    }
    
    public boolean visit(ReturnC construct) {
        return trap(construct);
    }
    
    void endVisit(ReturnC construct) {
    }
    
    public boolean visit(BinaryC construct) {
        return trap(construct);
    }
    
    public boolean visit(IntegerLiteralC construct) {
        return trap(construct);
    }
    
    public boolean visit(StringLiteralC construct) {
        return trap(construct);
    }
    
    public boolean visit(VariableReferenceC construct) {
        return trap(construct);
    }
    
}
