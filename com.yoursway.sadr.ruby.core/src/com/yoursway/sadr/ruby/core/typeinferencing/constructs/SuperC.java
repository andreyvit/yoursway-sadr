package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ruby.ast.RubySuperExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;

public class SuperC extends RubyConstructImpl<RubySuperExpression> {
    
    SuperC(RubyStaticContext sc, RubySuperExpression node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(RubyDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        ValueInfo selfType = dc.selfType();
        if (selfType != null)
            return continuation.consume(selfType, requestor);
        else
            return continuation.consume(emptyValueInfo(), requestor);
    }
    
}
