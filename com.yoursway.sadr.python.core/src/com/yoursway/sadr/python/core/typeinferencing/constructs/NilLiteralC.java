package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.references.SimpleReference;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.simple_types.SimpleType;
import com.yoursway.sadr.blocks.simple_types.SimpleTypeItem;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.values.NilValue;

public class NilLiteralC extends PythonConstructImpl<SimpleReference> {
    
    NilLiteralC(PythonStaticContext sc, SimpleReference node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        SimpleType t = staticContext().builtins().nilType();
        builder.add(new SimpleTypeItem(t), new NilValue());
        return continuation.consume(builder.build(), requestor);
    }
    
}
