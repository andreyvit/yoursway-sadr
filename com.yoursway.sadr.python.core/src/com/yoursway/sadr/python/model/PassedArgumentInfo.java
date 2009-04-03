package com.yoursway.sadr.python.model;

import java.util.List;

import com.yoursway.sadr.python.constructs.CallableDeclaration;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonScope;
import com.yoursway.sadr.python.index.unodes.Alias;
import com.yoursway.sadr.python_v2.croco.DeclaredArguments;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;

public abstract class PassedArgumentInfo {
    
    public final void computeAliases(CallableDeclaration decl, PythonDynamicContext dc, List<Alias> unodes) {
        computeAliases(decl.getDeclaredArguments(), decl, dc, unodes);
    }
    
    protected abstract void computeAliases(DeclaredArguments declaredArguments, PythonScope scope,
            PythonDynamicContext dc, List<Alias> unodes);
    
    public abstract PassedArgumentInfo translateToUnbound();
    
    public abstract PythonConstruct getCallable();
    
}
