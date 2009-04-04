package com.yoursway.sadr.python.analysis.lang.unodes;

import java.util.Collection;
import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.Alias;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.HeadPunode;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.Punode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

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
    public abstract PythonValueSet calculateValue(PythonStaticContext sc, PythonDynamicContext dc);
    
    public abstract VariableUnode leadingVariableUnode();
    
    public abstract boolean isIndexable();
    
    @pausable
    public abstract void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc,
            Set<Alias> aliases);
    
    @pausable
    public void findIntegerIndexRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc,
            Set<Alias> aliases, int index) {
    }
    
    @pausable
    public void findUnknownIndexRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc,
            Set<Alias> aliases) {
    }
    
    public PythonValueSet calculateLiteralValue() {
        return null;
    }
    
    public final void addGenericVariationsTo(Collection<Unode> alternatives, boolean reading) {
        addGenericVariationsTo(alternatives, new HeadPunode(this), reading);
    }
    
    protected abstract void addGenericVariationsTo(Collection<Unode> alternatives, Punode punode, boolean reading);
    
}
