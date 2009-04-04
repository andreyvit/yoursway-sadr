package com.yoursway.sadr.python.analysis.lang.unodes.indexable;

import static java.lang.String.format;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.aliasing.Alias;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class VariableUnode extends AbstractIndexableUnode {
    
    private final String name;
    
    public VariableUnode(String name) {
        if (name == null)
            throw new NullPointerException("name is null");
        this.name = name;
        this.hashCode = computeHashCode();
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return format("$%s", name);
    }
    
    @Override
    public int computeHashCode() {
        final int prime = 31;
        int result = 42;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        VariableUnode other = (VariableUnode) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    @Override
    @pausable
    public PythonValueSet calculateValue(PythonStaticContext sc, PythonDynamicContext dc) {
        return new Alias(this, sc, dc).queryIndexForValuesAssignedTo();
    }
    
    @Override
    public VariableUnode leadingVariableUnode() {
        return this;
    }
    
    @Override
    public void addGenericVariationsTo(Collection<Unode> alternatives, Suffix suffix, boolean reading) {
        alternatives.add(suffix.appendTo(this));
    }
    
}
