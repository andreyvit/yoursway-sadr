package com.yoursway.sadr.python.index.unodes;

import java.util.List;

import kilim.pausable;

import com.yoursway.sadr.python.constructs.PythonScope;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.index.punodes.Punode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

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
    
    public abstract Punode punodize();
    
    @pausable
    public abstract PythonValueSet calculateValue(PythonStaticContext staticContext, PythonDynamicContext dc,
            List<PythonScope> currentScopes);
    
    public abstract VariableUnode leadingVariableUnode();
    
    public abstract boolean isIndexable();
    
}
