package com.yoursway.sadr.python.index.unodes;

public abstract class Unode {
    
    protected int hashCode;
    
    public Unode() {
    }
    
    @Override
    public abstract String toString();
    
    @Override
    public abstract boolean equals(Object obj);
    
    protected abstract int computeHashCode();
    
    @Override
    public final int hashCode() {
        return hashCode;
    }
    
}
