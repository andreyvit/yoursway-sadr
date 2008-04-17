package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.expressions.StringLiteral;

import com.yoursway.sadr.blocks.simple_types.PythonSimpleType;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.python.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;

public class StringLiteralC extends PythonConstructImpl<StringLiteral> {
    
    StringLiteralC(PythonStaticContext sc, StringLiteral node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        PythonSimpleType t = staticContext().builtins().stringType();
        builder.add(new SimpleType(t), new StringValue(stringValue()));
        return continuation.consume(builder.build(), requestor);
    }
    
    private String stringValue() {
        String v = node.getValue();
        v = unquote(v);
        v = v.replaceAll("\\n", "\n");
        v = v.replaceAll("<CR>", "\n");
        return v;
    }
    
    private static String unquote(String text) {
        if (text.startsWith("\"\"\"") && text.endsWith("\"\"\"") || text.startsWith("'''")
                && text.endsWith("'''"))
            return text.substring(3, text.length() - 3);
        if (text.startsWith("\"") && text.endsWith("\"") || text.startsWith("'") && text.endsWith("'")
                || text.startsWith("`") && text.endsWith("`"))
            return text.substring(1, text.length() - 1);
        return text;
    }
    
}
