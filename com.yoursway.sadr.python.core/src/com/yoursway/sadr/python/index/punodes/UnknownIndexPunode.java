package com.yoursway.sadr.python.index.punodes;

import com.yoursway.sadr.python.index.unodes.UnknownIndexUnode;
import com.yoursway.sadr.python.index.unodes.Unode;

public class UnknownIndexPunode extends TailPunode {
    
    public UnknownIndexPunode(Punode head) {
        super(head);
    }
    
    @Override
    public String toString() {
        return head + "[?]";
    }
    
    @Override
    public Unode wrap(Unode replacementUnode) {
        return new UnknownIndexUnode(replacementUnode);
    }
    
    @Override
    protected Punode wrap(Punode punode) {
        return new UnknownIndexPunode(head.wrap(punode));
    }
}
