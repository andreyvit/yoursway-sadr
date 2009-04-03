package com.yoursway.sadr.python.index.unodes;

import java.util.Collection;
import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.python.constructs.PythonAnalHelpers;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.index.punodes.AnyIndexPunode;
import com.yoursway.sadr.python.index.punodes.HeadPunode;
import com.yoursway.sadr.python.index.punodes.Punode;
import com.yoursway.sadr.python.index.punodes.UnknownIndexPunode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public final class AnyIndexUnode extends Unode {
    
    private final Unode receiver;
    
    public AnyIndexUnode(Unode receiver) {
        if (receiver == null)
            throw new NullPointerException("receiver is null");
        this.receiver = receiver;
    }
    
    @Override
    @pausable
    public PythonValueSet calculateValue(PythonStaticContext sc, PythonDynamicContext dc) {
        return trackAssignmentsAndRenames(sc, dc);
    }
    
    @pausable
    private PythonValueSet trackAssignmentsAndRenames(PythonStaticContext sc, PythonDynamicContext dc) {
        Set<Alias> aliases = PythonAnalHelpers.computeAliases(new Alias(this, sc, dc));
        return PythonAnalHelpers.queryIndexForValuesAssignedTo(aliases);
    }
    
    @Override
    protected int computeHashCode() {
        return receiver.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        AnyIndexUnode that = (AnyIndexUnode) obj;
        return this.receiver.equals(that.receiver);
    }
    
    @Override
    public boolean isIndexable() {
        return receiver.isIndexable();
    }
    
    @Override
    public VariableUnode leadingVariableUnode() {
        return receiver.leadingVariableUnode();
    }
    
    @Override
    public Punode punodize() {
        return new AnyIndexPunode(new HeadPunode(receiver));
    }
    
    @Override
    public String toString() {
        return receiver + "[*]";
    }
    
    @Override
    @pausable
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc, Set<Alias> aliases) {
        if (isIndexable())
            PythonAnalHelpers.computeRenamesForAliasingUsingIndex(punode, sc, dc, aliases);
        receiver.findUnknownIndexRenames(punode, sc, dc, aliases);
    }
    
    @Override
    protected void addGenericVariationsTo(Collection<Unode> alternatives, Punode punode, boolean reading) {
        receiver.addGenericVariationsTo(alternatives, new UnknownIndexPunode(punode), reading);
    }
    
}
