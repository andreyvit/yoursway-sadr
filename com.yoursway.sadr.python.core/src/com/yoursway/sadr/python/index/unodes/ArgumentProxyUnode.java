package com.yoursway.sadr.python.index.unodes;

import java.util.Collection;
import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.constructs.ArgumentProxyC;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.index.punodes.Punode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

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
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc, Set<Alias> aliases) {
        arg.findRenames(punode, sc, dc, aliases);
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
    public Punode punodize() {
        return null;
    }
    
    @Override
    public String toString() {
        return "ARG";
    }
    
    @Override
    protected void addGenericVariationsTo(Collection<Unode> alternatives, Punode punode, boolean reading) {
    }
    
}
