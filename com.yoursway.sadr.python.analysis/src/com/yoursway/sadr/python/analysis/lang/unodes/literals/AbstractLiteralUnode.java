package com.yoursway.sadr.python.analysis.lang.unodes.literals;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

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
    
    @Override
    @pausable
    public final PythonValueSet calculateValue(PythonLexicalContext sc, PythonDynamicContext dc) {
        return calculateLiteralValue();
    }
    
}
