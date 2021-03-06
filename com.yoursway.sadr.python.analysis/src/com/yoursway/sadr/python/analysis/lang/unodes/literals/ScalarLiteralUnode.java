package com.yoursway.sadr.python.analysis.lang.unodes.literals;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class ScalarLiteralUnode extends AbstractLiteralUnode {
    
    public static final Unode UNKNOWN = new ScalarLiteralUnode(PythonValueSet.EMPTY);
    
    private final PythonValueSet valueSet;
    
    public ScalarLiteralUnode(PythonValueSet valueSet) {
        if (valueSet == null)
            throw new NullPointerException("valueSet is null");
        this.valueSet = valueSet;
    }
    
    @Override
    public PythonValueSet calculateLiteralValue() {
        return valueSet;
    }
    
    @Override
    protected int computeHashCode() {
        return valueSet.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        ScalarLiteralUnode that = (ScalarLiteralUnode) obj;
        return this.valueSet.equals(that.valueSet);
    }
    
    @Override
    @pausable
    public void findRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
    }
    
    @Override
    public String toString() {
        return valueSet.toString();
    }
    
}
