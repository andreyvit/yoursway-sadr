package com.yoursway.sadr.python.analysis.lang.unodes.literals;

import java.util.HashMap;
import java.util.Map;

import kilim.pausable;

import com.google.common.base.Join;
import com.yoursway.sadr.python.analysis.PythonAnalHelpers;
import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.objectmodel.values.DictValue;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class DictLiteralUnode extends AbstractLiteralUnode {
    
    private final Map<String, Bnode> items;
    
    public DictLiteralUnode(Map<String, Bnode> items) {
        if (items == null)
            throw new NullPointerException("items is null");
        this.items = new HashMap<String, Bnode>(items);
        this.hashCode = items.hashCode();
    }
    
    public int itemCount() {
        return items.size();
    }
    
    @Override
    public PythonValueSet calculateLiteralValue() {
        return new PythonValueSet(new DictValue());
    }
    
    @Override
    protected int computeHashCode() {
        return hashCode;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        DictLiteralUnode that = (DictLiteralUnode) obj;
        return this.items.equals(that.items);
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
        Bnode bnode = items.get(index);
        if (bnode != null)
            PythonAnalHelpers.addRenameForConstruct(suffix, aliases, bnode, dc);
    }
    
    @Override
    @pausable
    public void findUnknownIndexRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        for (Bnode item : items.values())
            PythonAnalHelpers.addRenameForConstruct(suffix, aliases, item, dc);
    }
    
    @Override
    public String toString() {
        return "[" + Join.join(", ", items.entrySet()) + "]";
    }
    
}
