package com.yoursway.sadr.python.analysis.lang.unodes.proxies;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.objectmodel.values.DictValue;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class DictConcatUnode extends Unode {
    
    private final Unode first;
    private final Unode second;
    
    public DictConcatUnode(Unode first, Unode second) {
        if (first == null)
            throw new NullPointerException("first is null");
        if (second == null)
            throw new NullPointerException("second is null");
        this.first = first;
        this.second = second;
    }
    
    @Override
    public void addGenericVariationsTo(Collection<Unode> alternatives, Suffix suffix, boolean reading) {
    }
    
    @Override
    @pausable
    public PythonValueSet calculateValue(PythonLexicalContext sc, PythonDynamicContext dc) {
        return new PythonValueSet(new DictValue());
    }
    
    @Override
    protected int computeHashCode() {
        return first.hashCode() ^ second.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        DictConcatUnode that = (DictConcatUnode) obj;
        return this.first.equals(that.first) && this.second.equals(that.second);
    }
    
    @Override
    @pausable
    public void findRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
    }
    
    @Override
    @pausable
    public void findStringIndexRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases, String index) {
        first.findStringIndexRenames(suffix, sc, dc, aliases, index);
        second.findStringIndexRenames(suffix, sc, dc, aliases, index);
    }
    
    @Override
    @pausable
    public void findUnknownIndexRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        first.findUnknownIndexRenames(suffix, sc, dc, aliases);
        second.findUnknownIndexRenames(suffix, sc, dc, aliases);
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
        return first + " .DICTCAT. " + second;
    }
    
}
