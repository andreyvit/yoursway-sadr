package com.yoursway.sadr.python.analysis.lang.unodes.punodes;

import com.yoursway.sadr.python.analysis.lang.unodes.UnknownIndexUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;

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