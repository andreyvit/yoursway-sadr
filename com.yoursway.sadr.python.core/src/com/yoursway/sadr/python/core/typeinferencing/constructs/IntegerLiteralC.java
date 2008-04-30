package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.expressions.NumericLiteral;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class IntegerLiteralC extends PythonConstructImpl<NumericLiteral> {
    
    IntegerLiteralC(Scope sc, NumericLiteral node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return staticContext().schema().integerTypesSupport.evaluateIntegerLiteral(staticContext()
                .runtimeModel(), node.getIntValue(), requestor, continuation);
    }
    
}
