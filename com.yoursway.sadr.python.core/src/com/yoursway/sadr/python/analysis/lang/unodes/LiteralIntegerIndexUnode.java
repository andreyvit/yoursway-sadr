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
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.LiteralIntegerIndexPunode;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.Punode;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.UnknownIndexPunode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class LiteralIntegerIndexUnode extends Unode {
    
    private final Unode receiver;
    private final int index;
    
    public LiteralIntegerIndexUnode(Unode receiver, int index) {
        if (receiver == null)
            throw new NullPointerException("receiver is null");
        this.receiver = receiver;
        this.index = index;
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
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + index;
        result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        LiteralIntegerIndexUnode other = (LiteralIntegerIndexUnode) obj;
        if (index != other.index)
            return false;
        if (receiver == null) {
            if (other.receiver != null)
                return false;
        } else if (!receiver.equals(other.receiver))
            return false;
        return true;
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
        return new LiteralIntegerIndexPunode(new HeadPunode(receiver), index);
    }
    
    @Override
    public String toString() {
        return receiver + "[" + index + "]";
    }
    
    @Override
    @pausable
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc, Set<Alias> aliases) {
        if (isIndexable())
            PythonAnalHelpers.computeRenamesForAliasingUsingIndex(punode, sc, dc, aliases);
        receiver.findIntegerIndexRenames(punode, sc, dc, aliases, index);
    }
    
    @Override
    protected void addGenericVariationsTo(Collection<Unode> alternatives, Punode punode, boolean reading) {
        receiver.addGenericVariationsTo(alternatives, new LiteralIntegerIndexPunode(punode, index), reading);
        if (reading)
            receiver.addGenericVariationsTo(alternatives, new UnknownIndexPunode(punode), reading);
        else
            receiver.addGenericVariationsTo(alternatives, new AnyIndexPunode(punode), reading);
    }
    
}
