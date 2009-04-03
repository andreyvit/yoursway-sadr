package com.yoursway.sadr.python.index.punodes;

import com.yoursway.sadr.python.index.unodes.Unode;

public class HeadPunode extends Punode {
    
    private final Unode head;
    
    public HeadPunode(Unode head) {
        if (head == null)
            throw new NullPointerException("head is null");
        this.head = head;
    }
    
    @Override
    public Unode getHead() {
        return head;
    }
    
    @Override
    public Unode wrap(Unode replacementUnode) {
        return replacementUnode;
    }
    
    @Override
    protected Punode wrap(Punode punode) {
        return punode;
    }
    
    @Override
    public String toString() {
        return head.toString() + " | ";
    }
    
}
