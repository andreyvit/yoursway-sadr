package com.yoursway.sadr.python.analysis.index.wrapping;

import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.AttributeUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;

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
