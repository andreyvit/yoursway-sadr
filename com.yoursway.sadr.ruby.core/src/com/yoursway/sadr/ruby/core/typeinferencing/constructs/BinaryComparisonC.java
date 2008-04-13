package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.BinaryCoercion;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.BinaryCoercionRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.ruby.core.typeinferencing.values.IntegerValue;

public class BinaryComparisonC extends RubyConstructImpl<CallExpression> {
    
    private final Comparison comparison;
    
    BinaryComparisonC(RubyStaticContext sc, CallExpression node, Comparison comparison) {
        super(sc, node);
        this.comparison = comparison;
    }
    
    public ContinuationRequestorCalledToken evaluateValue(final RubyDynamicContext dc,
            final InfoKind infoKind, ContinuationScheduler requestor, final ValueInfoContinuation continuation) {
        final RubyConstruct leftArg = wrap(innerContext(), node.getReceiver());
        final RubyConstruct rightArg = wrap(innerContext(), RubyUtils.argumentsOf(node)[0]);
        return requestor.schedule(new Continuation() {
            
            private final ValueInfoGoal leftGoal = new ExpressionValueInfoGoal(leftArg, dc, infoKind);
            
            private final ValueInfoGoal rightGoal = new ExpressionValueInfoGoal(rightArg, dc, infoKind);
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                requestor.subgoal(leftGoal);
                requestor.subgoal(rightGoal);
            }
            
            public void done(ContinuationScheduler requestor) {
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
                    
                    @SuppressWarnings("unchecked")
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
