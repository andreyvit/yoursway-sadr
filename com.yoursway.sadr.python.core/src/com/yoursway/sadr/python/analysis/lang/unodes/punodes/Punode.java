package com.yoursway.sadr.python.analysis.lang.unodes.punodes;

import com.yoursway.sadr.python.analysis.lang.unodes.Unode;

public abstract class Punode {
    
    public abstract Unode getHead();
    
    public abstract Unode wrap(Unode replacementUnode);
    
    protected abstract Punode wrap(Punode punode);
    
    public final Punode punodize() {
        Punode punode = getHead().punodize();
        if (punode == null)
            return null;
        return wrap(punode);
    }
    
}
