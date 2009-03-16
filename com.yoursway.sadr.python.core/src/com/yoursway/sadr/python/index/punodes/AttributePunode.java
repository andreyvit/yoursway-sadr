package com.yoursway.sadr.python.index.punodes;

import static java.lang.String.format;

import com.yoursway.sadr.python.index.unodes.AttributeUnode;
import com.yoursway.sadr.python.index.unodes.Unode;

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
        return format("Attr(%s)", tailAttrName);
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
