package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.InfoKind;

public class PythonFileC extends PythonConstructImpl<ModuleDeclaration> {
    
    public PythonFileC(PythonStaticContext sc, ModuleDeclaration node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        throw new UnsupportedOperationException();
    }
    
}
