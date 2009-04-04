package com.yoursway.sadr.python.analysis.lang.unodes.literals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kilim.pausable;

import com.google.common.base.Join;
import com.yoursway.sadr.python.analysis.Alias;
import com.yoursway.sadr.python.analysis.PythonAnalHelpers;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.Punode;
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
    @pausable
    public PythonValueSet calculateValue(PythonStaticContext sc, PythonDynamicContext dc) {
        return new PythonValueSet(new ListValue());
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
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc, Set<Alias> aliases) {
    }
    
    @Override
    @pausable
    public void findIntegerIndexRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc,
            Set<Alias> aliases, int index) {
        if (index < items.size())
            PythonAnalHelpers.addRenameForConstruct(punode, aliases, items.get(index), dc);
    }
    
    @Override
    @pausable
    public void findUnknownIndexRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc,
            Set<Alias> aliases) {
        for (PythonConstruct item : items)
            PythonAnalHelpers.addRenameForConstruct(punode, aliases, item, dc);
    }
    
    @Override
    public String toString() {
        return "[" + Join.join(", ", items) + "]";
    }
    
}
