package com.yoursway.sadr.python.analysis.index.data;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.CallableDeclaration;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;

public abstract class PassedArgumentInfo {
    
    public final void computeAliases(CallableDeclaration decl, PythonDynamicContext dc, Suffix suffix,
            AliasConsumer aliases) {
        computeAliases(decl.getDeclaredArguments(), decl.getInnerLC(), dc, suffix, aliases);
    }
    
    protected abstract void computeAliases(DeclaredArguments declaredArguments, PythonLexicalContext lc,
            PythonDynamicContext dc, Suffix suffix, AliasConsumer aliases);
    
    public abstract PassedArgumentInfo translateToUnbound();
    
    public abstract Bnode getCallable();
    
}
