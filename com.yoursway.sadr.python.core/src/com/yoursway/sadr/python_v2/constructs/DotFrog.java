package com.yoursway.sadr.python_v2.constructs;

import java.util.List;

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
    public boolean match(List<PythonConstruct> constructs) {
        return head.match(constructs) && constructs.get(getLength() - 1).match(this);
    }
}
