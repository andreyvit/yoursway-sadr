package com.yoursway.sadr.python.analysis.lang.unodes;

import java.util.Collection;
import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.Alias;
import com.yoursway.sadr.python.analysis.PythonAnalHelpers;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.AnyIndexPunode;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.HeadPunode;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.Punode;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.UnknownIndexPunode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public final class UnknownIndexUnode extends AbstractIndexableUnode {
    
    private final Unode receiver;
    
    public UnknownIndexUnode(Unode receiver) {
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
        UnknownIndexUnode that = (UnknownIndexUnode) obj;
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
        return new UnknownIndexPunode(new HeadPunode(receiver));
    }
    
    @Override
    public String toString() {
        return receiver + "[?]";
    }
    
    @Override
    @pausable
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc, Set<Alias> aliases) {
        super.findRenames(punode, sc, dc, aliases);
        receiver.findUnknownIndexRenames(punode, sc, dc, aliases);
    }
    
    @Override
    protected void addGenericVariationsTo(Collection<Unode> alternatives, Punode punode, boolean reading) {
        receiver.addGenericVariationsTo(alternatives, new AnyIndexPunode(punode), reading);
        if (!reading)
            receiver.addGenericVariationsTo(alternatives, new UnknownIndexPunode(punode), reading);
    }
    
}
