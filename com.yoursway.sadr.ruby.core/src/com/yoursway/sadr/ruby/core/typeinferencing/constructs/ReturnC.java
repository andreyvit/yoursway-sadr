package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import org.eclipse.dltk.ruby.ast.RubyCallArgument;
import org.eclipse.dltk.ruby.ast.RubyReturnStatement;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.ReturnsAffector;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.ReturnsRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;

public class ReturnC extends RubyConstructImpl<RubyReturnStatement> implements ReturnsAffector {
    
    public ReturnC(RubyStaticContext sc, RubyReturnStatement node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(RubyDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(ValueInfo.emptyValueInfo(), requestor);
    }
    
    public void actOnReturns(ReturnsRequest request) {
        RubyCallArgument arg = (RubyCallArgument) node.getValue().getChilds().get(0);
        request.add(wrap(innerContext(), arg.getValue()));
        //! several values returning
    }
}
