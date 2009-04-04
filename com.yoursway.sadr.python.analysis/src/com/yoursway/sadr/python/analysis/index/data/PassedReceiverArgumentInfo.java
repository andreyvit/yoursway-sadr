package com.yoursway.sadr.python.analysis.index.data;

import java.util.List;

import com.yoursway.sadr.python.analysis.aliasing.Alias;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.lexical.PythonScope;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.FieldAccessC;

public final class PassedReceiverArgumentInfo extends PassedArgumentInfo {
    
    private final FieldAccessC callable;
    
    public PassedReceiverArgumentInfo(FieldAccessC callable) {
        if (callable == null)
            throw new NullPointerException("callable is null");
        this.callable = callable;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((callable == null) ? 0 : callable.hashCode());
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
        PassedReceiverArgumentInfo other = (PassedReceiverArgumentInfo) obj;
        if (callable == null) {
            if (other.callable != null)
                return false;
        } else if (!callable.equals(other.callable))
            return false;
        return true;
    }
    
    @Override
    protected void computeAliases(DeclaredArguments declaredArguments, PythonScope scope, PythonDynamicContext dc, List<Alias> unodes) {
    }
    
    @Override
    public PassedArgumentInfo translateToUnbound() {
        return new PassedPositionalArgumentInfo(callable, 0);
    }
    
    @Override
    public PythonConstruct getCallable() {
        return callable;
    }
    
    @Override
    public String toString() {
        return "self arg of " + callable;
    }
    
}
