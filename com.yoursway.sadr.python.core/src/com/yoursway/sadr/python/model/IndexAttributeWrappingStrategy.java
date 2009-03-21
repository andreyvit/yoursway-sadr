package com.yoursway.sadr.python.model;

import com.yoursway.sadr.python.index.unodes.AttributeUnode;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python.index.unodes.VariableUnode;

public class IndexAttributeWrappingStrategy extends IndexNameWrappingStrategy {
    
    private final Unode receiver;
    
    public IndexAttributeWrappingStrategy(Unode receiver) {
        if (receiver == null)
            throw new NullPointerException("receiver is null");
        this.receiver = receiver;
    }
    
    @Override
    public Unode wrap(Unode unode) {
        if (unode instanceof VariableUnode)
            return new AttributeUnode(receiver, ((VariableUnode) unode).getName());
        return unode;
    }
    
}
