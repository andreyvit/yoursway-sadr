package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.python.parser.ast.statements.ReturnStatement;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.ReturnsAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.ReturnsRequest;

public class ReturnC extends PythonConstructImpl<ReturnStatement> implements ReturnsAffector {
    
    ReturnC(PythonStaticContext sc, ReturnStatement node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
    public void actOnReturns(ReturnsRequest request) {
        request.add(wrap(innerContext(), node.getExpression()));
    }
    
}
