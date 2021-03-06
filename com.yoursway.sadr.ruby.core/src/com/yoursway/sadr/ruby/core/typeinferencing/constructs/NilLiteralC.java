package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.expressions.NilLiteral;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubySimpleType;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.ruby.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.ruby.core.typeinferencing.values.NilValue;

public class NilLiteralC extends RubyConstructImpl<NilLiteral> {
    
    NilLiteralC(RubyStaticContext sc, NilLiteral node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(RubyDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        RubySimpleType t = staticContext().builtins().nilType();
        builder.add(new SimpleType(t), new NilValue());
        return continuation.consume(builder.build(), requestor);
    }
    
}
