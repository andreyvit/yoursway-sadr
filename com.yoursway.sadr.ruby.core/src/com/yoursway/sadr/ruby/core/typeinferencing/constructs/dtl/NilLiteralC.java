package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import org.eclipse.dltk.ast.expressions.NilLiteral;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubySimpleType;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.ruby.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.ruby.core.typeinferencing.values.NilValue;

public class NilLiteralC extends DtlConstruct<NilLiteral> {
    
    NilLiteralC(RubyStaticContext sc, NilLiteral node) {
        super(sc, node);
    }
    
    public void evaluateValue(RubyDynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        RubySimpleType t = rubyStaticContext().builtins().nilType();
        builder.add(new SimpleType(t), new NilValue());
        continuation.consume(builder.build(), requestor);
    }
    
}
