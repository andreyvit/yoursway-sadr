package com.yoursway.sadr.python.analysis.lang.unodes.literals;

import java.util.Collection;

import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;

public abstract class AbstractLiteralUnode extends Unode {
    
    @Override
    public boolean isIndexable() {
        return false;
    }
    
    @Override
    public VariableUnode leadingVariableUnode() {
        return null;
    }
    
    @Override
    public final void addGenericVariationsTo(Collection<Unode> alternatives, Suffix suffix, boolean reading) {
    }
    
}
