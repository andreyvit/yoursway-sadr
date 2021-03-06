package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.expressions.StringLiteral;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubySimpleType;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.ruby.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.ruby.core.typeinferencing.values.StringValue;

public class StringLiteralC extends RubyConstructImpl<StringLiteral> {
    
    StringLiteralC(RubyStaticContext sc, StringLiteral node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(RubyDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        RubySimpleType t = staticContext().builtins().stringType();
        builder.add(new SimpleType(t), new StringValue(stringValue()));
        return continuation.consume(builder.build(), requestor);
    }
    
    private String stringValue() {
        String v = node.getValue();
        v = v.replaceAll("\\n", "\n");
        v = v.replaceAll("<CR>", "\n");
        return v;
    }
    
}
