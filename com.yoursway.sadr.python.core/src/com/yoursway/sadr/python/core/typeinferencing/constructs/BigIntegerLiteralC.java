package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.math.BigInteger;

import org.eclipse.dltk.ast.expressions.BigNumericLiteral;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.integer_literals.LongValue;
import com.yoursway.sadr.blocks.integer_literals.RuntimeModelWithIntegerTypes;
import com.yoursway.sadr.blocks.simple_types.SimpleType;
import com.yoursway.sadr.blocks.simple_types.SimpleTypeItem;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class BigIntegerLiteralC extends PythonConstructImpl<BigNumericLiteral> {
    
    BigIntegerLiteralC(Scope sc, BigNumericLiteral literal) {
        super(sc, literal);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        
        RuntimeModelWithIntegerTypes modelWithIntegerTypes = staticContext().schema().integerTypesSupport
                .facelet(staticContext().runtimeModel());
        SimpleType t = modelWithIntegerTypes.longType();
        BigInteger v = node.getLongValue();
        builder.add(new SimpleTypeItem(t), new LongValue(v));
        
        return continuation.consume(builder.build(), requestor);
    }
}
