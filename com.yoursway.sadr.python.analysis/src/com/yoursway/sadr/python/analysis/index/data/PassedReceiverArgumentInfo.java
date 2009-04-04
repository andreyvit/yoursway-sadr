package com.yoursway.sadr.python.analysis.index.data;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;

public final class PassedReceiverArgumentInfo extends PassedArgumentInfo {
    
    private final Bnode callable;
    
    public PassedReceiverArgumentInfo(Bnode callable) {
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
    protected void computeAliases(DeclaredArguments declaredArguments, PythonLexicalContext lc,
            PythonDynamicContext dc, Suffix suffix, AliasConsumer aliases) {
    }
    
    @Override
    public PassedArgumentInfo translateToUnbound() {
        return new PassedPositionalArgumentInfo(callable, 0);
    }
    
    @Override
    public Bnode getCallable() {
        return callable;
    }
    
    @Override
    public String toString() {
        return "self arg of " + callable;
    }
    
}
