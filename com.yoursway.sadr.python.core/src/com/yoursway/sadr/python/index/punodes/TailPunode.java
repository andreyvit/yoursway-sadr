package com.yoursway.sadr.python.index.punodes;

import com.yoursway.sadr.python.index.unodes.Unode;

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
