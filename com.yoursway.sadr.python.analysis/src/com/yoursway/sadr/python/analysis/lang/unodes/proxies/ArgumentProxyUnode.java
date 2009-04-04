package com.yoursway.sadr.python.analysis.lang.unodes.proxies;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.special.ArgumentProxyC;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public final class ArgumentProxyUnode extends Unode {
    
    private final ArgumentProxyC arg;
    
    public ArgumentProxyUnode(ArgumentProxyC arg) {
        if (arg == null)
            throw new NullPointerException("arg is null");
        this.arg = arg;
    }
    
    @Override
    @pausable
    public PythonValueSet calculateValue(PythonStaticContext sc, PythonDynamicContext dc) {
        return arg.evaluateValue(dc, InfoKind.VALUE);
    }
    
    @Override
    protected int computeHashCode() {
        return arg.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        return arg.equals(((ArgumentProxyUnode) obj).arg);
    }
    
    @Override
    @pausable
    public void findRenames(Suffix suffix, PythonStaticContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        arg.findRenames(suffix, sc, dc, aliases);
    }
    
    @Override
    public boolean isIndexable() {
        return false;
    }
    
    @Override
    public VariableUnode leadingVariableUnode() {
        return null;
    }
    
    @Override
    public String toString() {
        return "ARG";
    }
    
    @Override
    public void addGenericVariationsTo(Collection<Unode> alternatives, Suffix suffix, boolean reading) {
    }
    
}
