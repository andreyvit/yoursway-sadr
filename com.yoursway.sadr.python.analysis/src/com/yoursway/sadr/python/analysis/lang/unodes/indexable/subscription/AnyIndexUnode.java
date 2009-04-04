package com.yoursway.sadr.python.analysis.lang.unodes.indexable.subscription;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.unodes.ChainableSuffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.AbstractIndexableUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public final class AnyIndexUnode extends AbstractIndexableUnode {
    
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
    @pausable
    public void computeAliases(Suffix suffix, PythonStaticContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        super.computeAliases(suffix, sc, dc, aliases);
        receiver.computeAliases(new AnyIndexSuffix(suffix), sc, dc, aliases);
    }
    
    @Override
    public String toString() {
        return receiver + "[*]";
    }
    
    @Override
    @pausable
    public void findRenames(Suffix suffix, PythonStaticContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        super.findRenames(suffix, sc, dc, aliases);
        receiver.findUnknownIndexRenames(suffix, sc, dc, aliases);
    }
    
    @Override
    public void addGenericVariationsTo(Collection<Unode> alternatives, Suffix suffix, boolean reading) {
        receiver.addGenericVariationsTo(alternatives, new UnknownIndexUnode.UnknownIndexSuffix(suffix),
            reading);
    }
    
    static class AnyIndexSuffix extends ChainableSuffix {
        
        public AnyIndexSuffix(Suffix suffix) {
            super(suffix);
        }
        
        @Override
        public String thisSuffixToString() {
            return "[*]";
        }
        
        @Override
        protected Unode appendThisSuffixTo(Unode prefix) {
            return new AnyIndexUnode(prefix);
        }
        
    }
    
}
