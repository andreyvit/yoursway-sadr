package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ruby.ast.RubySuperExpression;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.DynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.StaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.ValueInfoContinuation;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;

public class SuperC extends DtlConstruct<RubySuperExpression> {
    
    SuperC(StaticContext sc, RubySuperExpression node) {
        super(sc, node);
    }
    
    public void evaluateValue(DynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        ValueInfo selfType = dc.selfType();
        if (selfType != null)
            continuation.consume(selfType, requestor);
        else
            continuation.consume(emptyValueInfo(), requestor);
    }
    
}
