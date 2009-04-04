package com.yoursway.sadr.python.analysis.index.data;

import java.util.List;

import com.yoursway.sadr.python.analysis.aliasing.Alias;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.lexical.PythonScope;
import com.yoursway.sadr.python.analysis.lang.constructs.CallableDeclaration;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;

public abstract class PassedArgumentInfo {
    
    public final void computeAliases(CallableDeclaration decl, PythonDynamicContext dc, List<Alias> unodes) {
        computeAliases(decl.getDeclaredArguments(), decl, dc, unodes);
    }
    
    protected abstract void computeAliases(DeclaredArguments declaredArguments, PythonScope scope,
            PythonDynamicContext dc, List<Alias> unodes);
    
    public abstract PassedArgumentInfo translateToUnbound();
    
    public abstract PythonConstruct getCallable();
    
}
