package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.python.core.runtime.std.StandardTypes;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.python.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python.core.typeinferencing.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.values.ValueTraits;

public class PythonValueAnalysis implements AnalysisProvider {
    
    public BinaryOperationHandler getBinaryPercentHandler() {
        return new BinaryOperationHandler() {
            public ValueInfo handleOperation(PythonConstructImpl<BinaryExpression> context,
                    ValueInfo leftInfo, ValueInfo rightInfo) {
                final StandardTypes builtins = context.staticContext().builtins();
                final ValueInfoBuilder builder = new ValueInfoBuilder();
                builder.add(new SimpleType(builtins.stringType()));
                
                for (Value left : leftInfo.containedValues()) {
                    ValueTraits lt = left.traits();
                    String fmt = lt.coherseToString();
                    if (lt.cohersibleToString()) {
                        for (Value right : rightInfo.containedValues()) {
                            ValueTraits rt = right.traits();
                            String arg = rt.coherseToString();
                            builder.add(new StringValue(String.format(fmt, arg)));
                        }
                    } else {
                        //FIXME: add __mod__ and __rmod__ handling
                    }
                }
                return builder.build();
            }
        };
    }
}
