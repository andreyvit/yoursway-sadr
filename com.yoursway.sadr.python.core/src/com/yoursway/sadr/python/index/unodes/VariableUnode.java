package com.yoursway.sadr.python.index.unodes;

import static java.lang.String.format;

import java.util.List;

import kilim.pausable;

import com.yoursway.sadr.python.constructs.PythonAnalHelpers;
import com.yoursway.sadr.python.constructs.PythonScope;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.index.punodes.Punode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class VariableUnode extends Unode {
    
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
        return format("Var(%s)", name);
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
    public Punode punodize() {
        return null;
    }
    
    @Override
    @pausable
    public PythonValueSet calculateValue(PythonStaticContext sc, PythonDynamicContext dc,
            List<PythonScope> scopes) {
        return PythonAnalHelpers.queryIndexForValuesAssignedTo(this, sc, dc, scopes);
    }
}
