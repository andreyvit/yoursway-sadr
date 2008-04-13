package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.expressions.BigNumericLiteral;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonSimpleType;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.python.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.python.core.typeinferencing.values.IntegerValue;

public class BigIntegerLiteralC extends PythonConstructImpl<BigNumericLiteral> {
    
    BigIntegerLiteralC(PythonStaticContext sc, BigNumericLiteral literal) {
        super(sc, literal);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        PythonSimpleType t = staticContext().builtins().intType();
        //FIXME: long v = node.getIntValue();
        long v = 0;
        builder.add(new SimpleType(t), new IntegerValue(v));
        return continuation.consume(builder.build(), requestor);
    }
}
