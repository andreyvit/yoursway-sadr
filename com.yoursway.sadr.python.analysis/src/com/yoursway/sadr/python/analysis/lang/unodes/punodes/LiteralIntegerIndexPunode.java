package com.yoursway.sadr.python.analysis.lang.unodes.punodes;

import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.subscription.LiteralIntegerIndexUnode;

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
