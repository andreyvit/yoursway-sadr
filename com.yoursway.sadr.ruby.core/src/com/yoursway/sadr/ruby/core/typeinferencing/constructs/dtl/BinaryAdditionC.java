package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ruby.ast.RubyCallArgument;

import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.ruby.core.runtime.std.StandardTypes;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.DynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.StaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.ValueInfoContinuation;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.BinaryCoercion;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.BinaryCoercionRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.ruby.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.ruby.core.typeinferencing.values.IntegerValue;
import com.yoursway.sadr.ruby.core.typeinferencing.values.StringValue;

public class BinaryAdditionC extends DtlConstruct<CallExpression> {
    
    BinaryAdditionC(StaticContext sc, CallExpression node) {
        super(sc, node);
    }
    
    public void evaluateValue(final DynamicContext dc, final InfoKind infoKind,
            ContinuationRequestor requestor, final ValueInfoContinuation continuation) {
        final ASTNode leftArg = node.getReceiver();
        ASTNode rightArg0 = (ASTNode) node.getArgs().getChilds().get(0);
        if (rightArg0 instanceof RubyCallArgument)
            rightArg0 = ((RubyCallArgument) rightArg0).getValue();
        final ASTNode rightArg = rightArg0;
        requestor.subgoal(new Continuation() {
            
            private final ValueInfoGoal leftGoal = new ExpressionValueInfoGoal((Scope) dc, leftArg, infoKind);
            
            private final ValueInfoGoal rightGoal = new ExpressionValueInfoGoal((Scope) dc, rightArg,
                    infoKind);
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                requestor.subgoal(leftGoal);
                requestor.subgoal(rightGoal);
            }
            
            public void done(ContinuationRequestor requestor) {
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
