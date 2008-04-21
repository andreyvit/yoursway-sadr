package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;

public class EvalRootC extends PythonConstructImpl<ModuleDeclaration> implements RootPythonConstruct {
    
    public EvalRootC(PythonStaticContext sc, ModuleDeclaration node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        throw new UnsupportedOperationException();
    }
    
    public PythonStaticContext innerStaticContext() {
        return innerContext();
    }
    
}
