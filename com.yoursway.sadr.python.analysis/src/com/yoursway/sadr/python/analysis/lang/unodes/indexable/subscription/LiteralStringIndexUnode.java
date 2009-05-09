package com.yoursway.sadr.python.analysis.lang.unodes.indexable.subscription;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.ChainableSuffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.AbstractIndexableUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class LiteralStringIndexUnode extends AbstractIndexableUnode {
    
    private final Unode receiver;
    private final String index;
    
    public LiteralStringIndexUnode(Unode receiver, String index) {
        if (receiver == null)
            throw new NullPointerException("receiver is null");
        if (index == null)
            throw new NullPointerException("index is null");
        this.receiver = receiver;
        this.index = index;
    }
    
    @Override
    @pausable
    public PythonValueSet calculateValue(PythonLexicalContext sc, PythonDynamicContext dc) {
        return trackAssignmentsAndRenames(sc, dc);
    }
    
    @Override
    protected int computeHashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + index.hashCode();
        result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        LiteralStringIndexUnode other = (LiteralStringIndexUnode) obj;
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
    @pausable
    public void computeAliases(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        super.computeAliases(suffix, sc, dc, aliases);
        receiver.computeAliases(new LiteralStringIndexSuffix(suffix, index), sc, dc, aliases);
    }
    
    @Override
    public String toString() {
        return receiver + "[" + index + "]";
    }
    
    @Override
    @pausable
    public void findRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        super.findRenames(suffix, sc, dc, aliases);
        receiver.findStringIndexRenames(suffix, sc, dc, aliases, index);
    }
    
    @Override
    public void addGenericVariationsTo(Collection<Unode> alternatives, Suffix suffix, boolean reading) {
        receiver.addGenericVariationsTo(alternatives, new LiteralStringIndexSuffix(suffix, index), reading);
        if (reading)
            receiver.addGenericVariationsTo(alternatives, new UnknownIndexUnode.UnknownIndexSuffix(suffix),
                reading);
        else
            receiver.addGenericVariationsTo(alternatives, new AnyIndexUnode.AnyIndexSuffix(suffix), reading);
    }
    
    static class LiteralStringIndexSuffix extends ChainableSuffix {
        
        private final String index;
        
        public LiteralStringIndexSuffix(Suffix suffix, String index) {
            super(suffix);
            this.index = index;
        }
        
        @Override
        public String thisSuffixToString() {
            return "[\"" + index + "\"]";
        }
        
        @Override
        protected Unode appendThisSuffixTo(Unode replacementUnode) {
            return new LiteralStringIndexUnode(replacementUnode, index);
        }
        
    }
    
}
