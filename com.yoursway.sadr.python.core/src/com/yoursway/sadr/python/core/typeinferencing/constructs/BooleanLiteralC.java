package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.simple_types.SimpleType;
import com.yoursway.sadr.blocks.simple_types.SimpleTypeItem;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python.core.typeinferencing.values.BooleanValue;

public class BooleanLiteralC extends PythonConstructImpl<VariableReference> {
    
    public BooleanLiteralC(Scope sc, VariableReference node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        String repr = node.getName();
        if (repr == null || !(repr.equals(BooleanValue.TRUE) || repr.equals(BooleanValue.FALSE)))
            throw new NullPointerException();
        ValueInfoBuilder builder = new ValueInfoBuilder();
        SimpleType t = staticContext().builtins().boolType();
        builder.add(new SimpleTypeItem(t), new BooleanValue(repr.equals(BooleanValue.TRUE)));
        return continuation.consume(builder.build(), requestor);
    }
    
}
