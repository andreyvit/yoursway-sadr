package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import org.eclipse.dltk.ruby.ast.RubyArrayAccessExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;

public class ArrayAccessC extends RubyConstructImpl<RubyArrayAccessExpression> {
    
    ArrayAccessC(RubyStaticContext sc, RubyArrayAccessExpression node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(final RubyDynamicContext dc,
            final InfoKind infoKind, ContinuationScheduler requestor, final ValueInfoContinuation continuation) {
        throw new UnsupportedOperationException();
    }
}
