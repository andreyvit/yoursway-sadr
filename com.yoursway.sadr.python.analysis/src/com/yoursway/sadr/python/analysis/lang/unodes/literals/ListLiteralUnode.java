package com.yoursway.sadr.python.analysis.lang.unodes.literals;

import java.util.ArrayList;
import java.util.List;

import kilim.pausable;

import com.google.common.base.Join;
import com.yoursway.sadr.python.analysis.PythonAnalHelpers;
import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.objectmodel.values.ListValue;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class ListLiteralUnode extends AbstractLiteralUnode {
    
    private final List<PythonConstruct> items;
    
    public ListLiteralUnode(List<PythonConstruct> items) {
        if (items == null)
            throw new NullPointerException("items is null");
        this.items = new ArrayList<PythonConstruct>(items);
        this.hashCode = items.hashCode();
    }
    
    @Override
    public PythonValueSet calculateLiteralValue() {
        return new PythonValueSet(new ListValue());
    }
    
    @Override
    protected int computeHashCode() {
        return hashCode;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        ListLiteralUnode that = (ListLiteralUnode) obj;
        return this.items.equals(that.items);
    }
    
    @Override
    @pausable
    public void findRenames(Suffix suffix, PythonStaticContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
    }
    
    @Override
    @pausable
    public void findIntegerIndexRenames(Suffix suffix, PythonStaticContext sc, PythonDynamicContext dc,
            AliasConsumer aliases, int index) {
        if (index < items.size())
            PythonAnalHelpers.addRenameForConstruct(suffix, aliases, items.get(index), dc);
    }
    
    @Override
    @pausable
    public void findUnknownIndexRenames(Suffix suffix, PythonStaticContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        for (PythonConstruct item : items)
            PythonAnalHelpers.addRenameForConstruct(suffix, aliases, item, dc);
    }
    
    @Override
    public String toString() {
        return "[" + Join.join(", ", items) + "]";
    }
    
}
