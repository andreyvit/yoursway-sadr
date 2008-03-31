package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python.core.typeinferencing.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.values.ValueTraits;

public class BinaryPercentC extends BinaryC {
    
    BinaryPercentC(PythonStaticContext sc, BinaryExpression node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(final PythonDynamicContext dc,
            final InfoKind infoKind, ContinuationScheduler requestor, final ValueInfoContinuation continuation) {
        final PythonConstruct leftArg = wrap(innerContext(), node.getLeft());
        final PythonConstruct rightArg = wrap(innerContext(), node.getRight());
        return requestor.schedule(new Continuation() {
            
            private final ValueInfoGoal leftGoal = new ExpressionValueInfoGoal(leftArg, dc, infoKind);
            
            private final ValueInfoGoal rightGoal = new ExpressionValueInfoGoal(rightArg, dc, infoKind);
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                requestor.subgoal(leftGoal);
                requestor.subgoal(rightGoal);
            }
            
            public void done(ContinuationScheduler requestor) {
                final StandardTypes builtins = staticContext().builtins();
                ValueInfo leftInfo = leftGoal.result(null);
                ValueInfo rightInfo = rightGoal.result(null);
                
                final ValueInfoBuilder builder = new ValueInfoBuilder();
                builder.add(new SimpleType(builtins.stringType()));
                
                for (Value left : leftInfo.containedValues()) {
                    ValueTraits lt = left.traits();
                    String fmt = lt.coherseToString();
                    for (Value right : rightInfo.containedValues()) {
                        ValueTraits rt = right.traits();
                        String arg = rt.coherseToString();
                        builder.add(new StringValue(String.format(fmt, arg)));
                    }
                }
                continuation.consume(builder.build(), requestor);
            }
            
        });
    }
}
