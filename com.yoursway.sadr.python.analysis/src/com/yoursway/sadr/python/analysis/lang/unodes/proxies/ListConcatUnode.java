package com.yoursway.sadr.python.analysis.lang.unodes.proxies;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.literals.ListLiteralUnode;
import com.yoursway.sadr.python.analysis.objectmodel.values.ListValue;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class ListConcatUnode extends Unode {
    
    private final ListLiteralUnode first;
    private final Unode second;
    
    public ListConcatUnode(ListLiteralUnode first, Unode second) {
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
        return new PythonValueSet(new ListValue());
    }
    
    @Override
    protected int computeHashCode() {
        return first.hashCode() ^ second.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        ListConcatUnode that = (ListConcatUnode) obj;
        return this.first.equals(that.first) && this.second.equals(that.second);
    }
    
    @Override
    @pausable
    public void findRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
    }
    
    @Override
    @pausable
    public void findIntegerIndexRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases, int index) {
        int firstCount = first.itemCount();
        if (index < firstCount)
            first.findIntegerIndexRenames(suffix, sc, dc, aliases, index);
        else
            second.findIntegerIndexRenames(suffix, sc, dc, aliases, index - firstCount);
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
        return first + " .LISTCAT. " + second;
    }
    
}
