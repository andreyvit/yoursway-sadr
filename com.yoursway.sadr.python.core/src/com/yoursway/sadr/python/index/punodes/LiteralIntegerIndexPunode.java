package com.yoursway.sadr.python.index.punodes;

import com.yoursway.sadr.python.index.unodes.LiteralIntegerIndexUnode;
import com.yoursway.sadr.python.index.unodes.Unode;

public class LiteralIntegerIndexPunode extends TailPunode {
    
    private final int index;
    
    public LiteralIntegerIndexPunode(Punode head, int index) {
        super(head);
        this.index = index;
    }
    
    @Override
    public String toString() {
        return head + "[" + index + "]";
    }
    
    @Override
    public Unode wrap(Unode replacementUnode) {
        return new LiteralIntegerIndexUnode(replacementUnode, index);
    }
    
    @Override
    protected Punode wrap(Punode punode) {
        return new LiteralIntegerIndexPunode(head.wrap(punode), index);
    }
    
}
