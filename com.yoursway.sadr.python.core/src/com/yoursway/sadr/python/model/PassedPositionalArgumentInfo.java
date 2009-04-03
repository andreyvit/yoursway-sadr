package com.yoursway.sadr.python.model;

import java.util.List;

import com.yoursway.sadr.python.constructs.ArgumentC;
import com.yoursway.sadr.python.constructs.CallC;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonScope;
import com.yoursway.sadr.python.index.unodes.Bnode;
import com.yoursway.sadr.python.index.unodes.VariableUnode;
import com.yoursway.sadr.python_v2.croco.DeclaredArguments;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;

public final class PassedPositionalArgumentInfo extends PassedCallArgumentInfo {
    
    private final int index;
    
    public PassedPositionalArgumentInfo(CallC call, int index) {
        this(call.getCallable(), index);
    }
    
    PassedPositionalArgumentInfo(PythonConstruct callable, int index) {
        super(callable);
        this.index = index;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((callable == null) ? 0 : callable.hashCode());
        result = prime * result + index;
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
        PassedPositionalArgumentInfo other = (PassedPositionalArgumentInfo) obj;
        if (callable == null) {
            if (other.callable != null)
                return false;
        } else if (!callable.equals(other.callable))
            return false;
        if (index != other.index)
            return false;
        return true;
    }
    
    @Override
    protected void computeAliases(DeclaredArguments declaredArguments, PythonScope scope,
            PythonDynamicContext dc, List<Bnode> unodes) {
        ArgumentC arg = declaredArguments.findPositional(index);
        if (arg != null)
            unodes.add(new Bnode(new VariableUnode(arg.getName()), scope, dc));
    }
    
    @Override
    public PassedArgumentInfo translateToUnbound() {
        return new PassedPositionalArgumentInfo(callable, index + 1);
    }
    
    @Override
    public String toString() {
        return "arg #" + index + " of " + callable;
    }
    
}
