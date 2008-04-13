package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;
import com.yoursway.sadr.python.core.typeinferencing.goals.BinaryCoercion;
import com.yoursway.sadr.python.core.typeinferencing.goals.BinaryCoercionRequestor;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.python.core.typeinferencing.values.IntegerValue;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;

public class BinaryAdditionC extends BinaryC {
    
    BinaryAdditionC(PythonStaticContext sc, BinaryExpression node) {
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
                BinaryCoercion coercion = new BinaryCoercion(staticContext().classLookup());
                ValueInfo leftInfo = leftGoal.result(null);
                ValueInfo rightInfo = rightGoal.result(null);
                
                final ValueInfoBuilder builder = new ValueInfoBuilder();
                coercion.coerce(leftInfo, rightInfo, new BinaryCoercionRequestor() {
                    
                    public void intType() {
                        builder.add(new SimpleType(builtins.intType()));
                    }
                    
                    public void ints(long a, long b) {
                        builder.add(new IntegerValue(a + b));
                    }
                    
                    public void stringType() {
                        builder.add(new SimpleType(builtins.stringType()));
                    }
                    
                    public void strings(String a, String b) {
                        builder.add(new StringValue(a + b));
                    }
                    
                    public void unknowns() {
                    }
                    
                });
                continuation.consume(builder.build(), requestor);
            }
            
        });
    }
}
