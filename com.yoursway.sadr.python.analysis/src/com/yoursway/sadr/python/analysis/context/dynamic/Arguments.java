package com.yoursway.sadr.python.analysis.context.dynamic;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.Starness;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public interface Arguments {
    
    @pausable
    public abstract PythonValueSet computeArgument(PythonDynamicContext dc, int index, String name, Bnode init, Starness starness);
    
    @pausable
    public abstract void findRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases, int index, String name, Bnode init, Starness starness);
    
}