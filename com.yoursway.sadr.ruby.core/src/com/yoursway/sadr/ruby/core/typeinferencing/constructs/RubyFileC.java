package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;

public class RubyFileC extends RubyConstructImpl<ModuleDeclaration> {
    
    public RubyFileC(RubyStaticContext sc, ModuleDeclaration node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(RubyDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        throw new UnsupportedOperationException();
    }
    
}
