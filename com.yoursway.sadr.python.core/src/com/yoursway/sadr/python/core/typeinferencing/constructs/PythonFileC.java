package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonModule;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.RootScope;

public class PythonFileC extends PythonConstructImpl<ModuleDeclaration> {
    
    private final FileScope innerScope;
    
    public PythonFileC(PythonStaticContext sc, ISourceModule module, PythonModule module2,
            ModuleDeclaration node) {
        super(sc, node);
        innerScope = new FileScope((RootScope) sc, module2, module, this);
    }
    
    @Override
    protected PythonStaticContext innerContext() {
        return innerScope;
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        throw new UnsupportedOperationException();
    }
    
}
