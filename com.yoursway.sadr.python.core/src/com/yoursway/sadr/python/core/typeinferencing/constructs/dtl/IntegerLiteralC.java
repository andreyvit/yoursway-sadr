package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import org.eclipse.dltk.ast.expressions.NumericLiteral;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.RubySimpleType;
import com.yoursway.sadr.python.core.typeinferencing.constructs.DynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.StaticContext;
import com.yoursway.sadr.python.core.typeinferencing.engine.ValueInfoContinuation;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.python.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.python.core.typeinferencing.values.IntegerValue;

public class IntegerLiteralC extends PythonConstruct<NumericLiteral> {
    
    IntegerLiteralC(StaticContext sc, NumericLiteral node) {
        super(sc, node);
    }
    
    public void evaluateValue(DynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        RubySimpleType t = staticContext().builtins().intType();
        long v = node.getIntValue();
        builder.add(new SimpleType(t), new IntegerValue(v));
        continuation.consume(builder.build(), requestor);
    }
    
}
