package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.DynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.StaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.ValueInfoContinuation;

public class EvalRootC extends DtlConstruct<ModuleDeclaration> {
    
    public EvalRootC(StaticContext sc, ModuleDeclaration node) {
        super(sc, node);
    }
    
    public void evaluateValue(DynamicContext dc, InfoKind infoKind,
            ContinuationRequestor requestor, ValueInfoContinuation continuation) {
        throw new UnsupportedOperationException();
    }
    
}
