package com.yoursway.sadr.python.index.unodes;

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
    
}
