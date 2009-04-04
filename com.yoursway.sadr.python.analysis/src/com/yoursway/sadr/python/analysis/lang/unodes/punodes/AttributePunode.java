package com.yoursway.sadr.python.analysis.lang.unodes.punodes;

import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.AttributeUnode;

public class AttributePunode extends TailPunode {
    
    private final String tailAttrName;
    
    public AttributePunode(Punode head, String tailAttrName) {
        super(head);
        if (tailAttrName == null)
            throw new NullPointerException("tailAttrName is null");
        this.tailAttrName = tailAttrName;
    }
    
    @Override
    public String toString() {
        return head + "." + tailAttrName;
    }
    
    @Override
    public Unode wrap(Unode replacementUnode) {
        return new AttributeUnode(replacementUnode, tailAttrName);
    }
    
    @Override
    protected Punode wrap(Punode punode) {
        return new AttributePunode(head.wrap(punode), tailAttrName);
    }
    
}
