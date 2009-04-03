package com.yoursway.sadr.python.index.unodes;

import java.util.Collection;

import com.yoursway.sadr.python.index.punodes.Punode;

public abstract class AbstractLiteralUnode extends Unode {
    
    @Override
    public boolean isIndexable() {
        return false;
    }
    
    @Override
    public Punode punodize() {
        return null;
    }
    
    @Override
    public VariableUnode leadingVariableUnode() {
        return null;
    }
    
    @Override
    protected final void addGenericVariationsTo(Collection<Unode> alternatives, Punode punode, boolean reading) {
    }
    
}
