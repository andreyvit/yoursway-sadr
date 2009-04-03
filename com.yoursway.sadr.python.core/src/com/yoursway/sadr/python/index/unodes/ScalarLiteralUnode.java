package com.yoursway.sadr.python.index.unodes;

import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.index.punodes.Punode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class ScalarLiteralUnode extends AbstractLiteralUnode {
    
    private final PythonValueSet valueSet;
    
    public ScalarLiteralUnode(PythonValueSet valueSet) {
        if (valueSet == null)
            throw new NullPointerException("valueSet is null");
        this.valueSet = valueSet;
    }
    
    @Override
    @pausable
    public PythonValueSet calculateValue(PythonStaticContext sc, PythonDynamicContext dc) {
        return valueSet;
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
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc, Set<Alias> aliases) {
    }
    
    @Override
    public String toString() {
        return valueSet.toString();
    }
    
}
