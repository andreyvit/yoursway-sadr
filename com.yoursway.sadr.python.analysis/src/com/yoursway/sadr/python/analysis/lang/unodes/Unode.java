package com.yoursway.sadr.python.analysis.lang.unodes;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
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
    
    @pausable
    public abstract PythonValueSet calculateValue(PythonLexicalContext sc, PythonDynamicContext dc);
    
    public abstract VariableUnode leadingVariableUnode();
    
    public abstract boolean isIndexable();
    
    @pausable
    public abstract void findRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases);
    
    @pausable
    public void findIntegerIndexRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases, int index) {
    }
    
    @pausable
    public void findStringIndexRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases, String index) {
    }
    
    @pausable
    public void findUnknownIndexRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
    }
    
    public PythonValueSet calculateLiteralValue() {
        return null;
    }
    
    public final void addGenericVariationsTo(Collection<Unode> alternatives, boolean reading) {
        addGenericVariationsTo(alternatives, Suffix.EMPTY, reading);
    }
    
    public abstract void addGenericVariationsTo(Collection<Unode> alternatives, Suffix suffix, boolean reading);
    
    @pausable
    public void computeAliases(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        findRenames(suffix, sc, dc, aliases);
    }
    
}
