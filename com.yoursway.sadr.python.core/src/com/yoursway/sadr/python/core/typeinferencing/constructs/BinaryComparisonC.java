package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.typeinferencing.goals.BinaryCoercion;
import com.yoursway.sadr.python.core.typeinferencing.goals.BinaryCoercionRequestor;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.python.core.typeinferencing.values.IntegerValue;

public class BinaryComparisonC extends BinaryC {
    
    private final Comparison comparison;
    
    BinaryComparisonC(PythonStaticContext sc, BinaryExpression node, Comparison comparison) {
        super(sc, node);
        this.comparison = comparison;
    }
    
    public void evaluateValue(final PythonDynamicContext dc, final InfoKind infoKind,
            ContinuationRequestor requestor, final ValueInfoContinuation continuation) {
        final PythonConstruct leftArg = wrap(innerContext(), node.getLeft());
        final PythonConstruct rightArg = wrap(innerContext(), node.getRight());
        requestor.subgoal(new Continuation() {
            
            private final ValueInfoGoal leftGoal = new ExpressionValueInfoGoal(leftArg, dc, infoKind);
            
            private final ValueInfoGoal rightGoal = new ExpressionValueInfoGoal(rightArg, dc, infoKind);
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                requestor.subgoal(leftGoal);
                requestor.subgoal(rightGoal);
            }
            
            public void done(ContinuationRequestor requestor) {
                BinaryCoercion coercion = new BinaryCoercion(staticContext().classLookup());
                ValueInfo leftInfo = leftGoal.result(null);
                ValueInfo rightInfo = rightGoal.result(null);
                
                final ValueInfoBuilder builder = new ValueInfoBuilder();
                builder.add(new SimpleType(staticContext().builtins().intType()));
                coercion.coerce(leftInfo, rightInfo, new BinaryCoercionRequestor() {
                    
                    public void intType() {
                    }
                    
                    public void stringType() {
                    }
                    
                    public void ints(long a, long b) {
                        compare(a, b);
                    }
                    
                    public void strings(String a, String b) {
                        compare(a, b);
                    }
                    
                    public void unknowns() {
                    }
                    
                    private <T> void compare(Comparable<T> a, T b) {
                        valueIs(comparison.matches(a.compareTo(b)));
                    }
                    
                    private void valueIs(boolean v) {
                        builder.add(new IntegerValue(v ? 1 : 0));
                    }
                    
                });
                continuation.consume(builder.build(), requestor);
            }
            
        });
    }
    
    private static final Map<String, Comparison> byOperator = createMap();
    
    private static Map<String, Comparison> createMap() {
        Map<String, Comparison> result = new HashMap<String, Comparison>();
        result.put("=", Comparison.EQUALS);
        result.put("<>", Comparison.NOT_EQUALS);
        result.put("!=", Comparison.NOT_EQUALS);
        result.put("<", Comparison.LESS);
        result.put("<=", Comparison.LESS_OR_EQUALS);
        result.put(">", Comparison.GREATER);
        result.put(">=", Comparison.GREATER_OR_EQUALS);
        return result;
    }
    
    public static Comparison parseComparison(String operator) {
        return byOperator.get(operator);
    }
    
}