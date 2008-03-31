package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonSimpleType;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.python.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.python.core.typeinferencing.values.BooleanValue;

public class BooleanLiteralC extends PythonConstructImpl<VariableReference> {
    
    public BooleanLiteralC(PythonStaticContext sc, VariableReference node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        String repr = node.getName();
        if (repr == null || !(repr.equals(BooleanValue.TRUE) || repr.equals(BooleanValue.FALSE)))
            throw new NullPointerException();
        ValueInfoBuilder builder = new ValueInfoBuilder();
        PythonSimpleType t = staticContext().builtins().boolType();
        builder.add(new SimpleType(t), new BooleanValue(repr.equals(BooleanValue.TRUE)));
        return continuation.consume(builder.build(), requestor);
    }
    
}
