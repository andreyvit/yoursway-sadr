package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;

public class PythonFileC extends PythonConstructImpl<ModuleDeclaration> {
    
    public PythonFileC(PythonStaticContext sc, ModuleDeclaration node) {
        super(sc, node);
    }
    
    public void evaluateValue(PythonDynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        throw new UnsupportedOperationException();
    }
    
}
