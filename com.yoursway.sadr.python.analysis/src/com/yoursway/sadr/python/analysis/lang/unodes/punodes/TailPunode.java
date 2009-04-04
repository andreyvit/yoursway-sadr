package com.yoursway.sadr.python.analysis.lang.unodes.punodes;

import com.yoursway.sadr.python.analysis.lang.unodes.Unode;

public abstract class TailPunode extends Punode {
    
    protected final Punode head;
    
    public TailPunode(Punode head) {
        if (head == null)
            throw new NullPointerException("head is null");
        this.head = head;
    }
    
    @Override
    public Unode getHead() {
        return head.getHead();
    }
    
}
