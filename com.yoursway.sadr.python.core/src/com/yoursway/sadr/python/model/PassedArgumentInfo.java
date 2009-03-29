package com.yoursway.sadr.python.model;

import java.util.List;

import com.yoursway.sadr.python.constructs.CallableDeclaration;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonScope;
import com.yoursway.sadr.python.index.unodes.Bnode;
import com.yoursway.sadr.python_v2.croco.DeclaredArguments;

public abstract class PassedArgumentInfo {
    
    public final void computeAliases(CallableDeclaration decl, List<Bnode> unodes) {
        computeAliases(decl.getDeclaredArguments(), decl, unodes);
    }
    
    protected abstract void computeAliases(DeclaredArguments declaredArguments, PythonScope scope,
            List<Bnode> unodes);
    
    public abstract PassedArgumentInfo translateToUnbound();
    
    public abstract PythonConstruct getCallable();
    
}
