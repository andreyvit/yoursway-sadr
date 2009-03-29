package com.yoursway.sadr.python.model;

import java.util.List;

import com.yoursway.sadr.python.constructs.ArgumentC;
import com.yoursway.sadr.python.constructs.CallC;
import com.yoursway.sadr.python.constructs.PythonScope;
import com.yoursway.sadr.python.index.unodes.Bnode;
import com.yoursway.sadr.python.index.unodes.VariableUnode;
import com.yoursway.sadr.python_v2.croco.DeclaredArguments;

public final class PassedKeywordArgumentInfo extends PassedCallArgumentInfo {
    
    private final String name;
    
    public PassedKeywordArgumentInfo(CallC call, String name) {
        super(call);
        this.name = name;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((callable == null) ? 0 : callable.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PassedKeywordArgumentInfo other = (PassedKeywordArgumentInfo) obj;
        if (callable == null) {
            if (other.callable != null)
                return false;
        } else if (!callable.equals(other.callable))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    @Override
    protected void computeAliases(DeclaredArguments declaredArguments, PythonScope scope, List<Bnode> unodes) {
        ArgumentC arg = declaredArguments.findKeyword(name);
        if (arg != null)
            unodes.add(new Bnode(new VariableUnode(arg.getName()), scope));
    }
    
    @Override
    public PassedArgumentInfo translateToUnbound() {
        return this;
    }
    
    @Override
    public String toString() {
        return "arg '" + name + "' of " + callable;
    }
    
}
