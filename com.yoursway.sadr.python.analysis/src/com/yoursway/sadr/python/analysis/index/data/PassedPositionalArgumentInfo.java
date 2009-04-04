package com.yoursway.sadr.python.analysis.index.data;

import java.util.List;

import com.yoursway.sadr.python.analysis.Alias;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.lexical.PythonScope;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.ArgumentC;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.CallC;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;

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
            PythonDynamicContext dc, List<Alias> unodes) {
        ArgumentC arg = declaredArguments.findPositional(index);
        if (arg != null)
            unodes.add(new Alias(new VariableUnode(arg.getName()), scope, dc));
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
