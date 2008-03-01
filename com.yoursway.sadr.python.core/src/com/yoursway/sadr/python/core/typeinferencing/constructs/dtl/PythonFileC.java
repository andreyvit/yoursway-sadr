package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.constructs.DynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.StaticContext;
import com.yoursway.sadr.python.core.typeinferencing.engine.ValueInfoContinuation;

public class PythonFileC extends PythonConstruct<ModuleDeclaration> {
    
    public PythonFileC(StaticContext sc, ModuleDeclaration node) {
        super(sc, node);
    }
    
    public void evaluateValue(DynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        throw new UnsupportedOperationException();
    }
    
}
