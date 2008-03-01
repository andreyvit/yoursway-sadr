package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.DynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.StaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.ValueInfoContinuation;

public class UnhandledC extends DtlConstruct<ASTNode> {
    
    UnhandledC(StaticContext sc, ASTNode node) {
        super(sc, node);
    }
    
    public void evaluateValue(DynamicContext dc, InfoKind infoKind,
            ContinuationRequestor requestor, ValueInfoContinuation continuation) {
        continuation.consume(emptyValueInfo(), requestor);
    }
    
}
