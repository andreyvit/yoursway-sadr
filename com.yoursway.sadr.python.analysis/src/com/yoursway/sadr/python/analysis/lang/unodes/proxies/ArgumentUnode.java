package com.yoursway.sadr.python.analysis.lang.unodes.proxies;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.Arguments;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public final class ArgumentUnode extends Unode {
    
    private final int index;
    private final String name;
    private final Bnode init;
    
    public ArgumentUnode(int index, String name, Bnode init) {
        this.index = index;
        this.name = name;
        this.init = init;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    @pausable
    public PythonValueSet calculateValue(PythonLexicalContext sc, PythonDynamicContext dc) {
        Arguments arguments = dc.argumentsOfTopCall();
        return arguments.computeArgument(dc, index, name, init);
    }
    
    @Override
    @pausable
    public void findRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        Arguments arguments = dc.argumentsOfTopCall();
        arguments.findRenames(suffix, sc, dc, aliases, index, name, init);
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
        return "ARG";
    }
    
    @Override
    public void addGenericVariationsTo(Collection<Unode> alternatives, Suffix suffix, boolean reading) {
    }
    
    @Override
    protected int computeHashCode() {
        return System.identityHashCode(this);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }
    
}
