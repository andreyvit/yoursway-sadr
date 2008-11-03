package com.yoursway.sadr.python_v2.croco;


public class DotFrog extends Frog {
    protected final Frog head;
    protected int length;
    
    public DotFrog(Frog head, String var) {
        super(var);
        this.head = head;
        this.length = super.getLength() + 1;
    }
    
    @Override
    public int getLength() {
        return this.length;
    }
    
    @Override
    public String toString() {
        return head.toString() + "." + name();
    }
    
    //    @Override
    //    public boolean match(PythonStatement statement) {
    //        if (statement instanceof AssignmentStatement) {
    //            
    //            return head.match(constructs) && constructs.get(getLength() - 1).match(this);
    //        }
    //    }
}
