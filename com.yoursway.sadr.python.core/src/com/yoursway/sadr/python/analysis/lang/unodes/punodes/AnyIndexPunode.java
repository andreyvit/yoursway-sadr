package com.yoursway.sadr.python.analysis.lang.unodes.punodes;

import com.yoursway.sadr.python.analysis.lang.unodes.AnyIndexUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;

public class AnyIndexPunode extends TailPunode {
    
    public AnyIndexPunode(Punode head) {
        super(head);
    }
    
    @Override
    public String toString() {
        return head + "[*]";
    }
    
    @Override
    public Unode wrap(Unode replacementUnode) {
        return new AnyIndexUnode(replacementUnode);
    }
    
    @Override
    protected Punode wrap(Punode punode) {
        return new AnyIndexPunode(head.wrap(punode));
    }
    
}