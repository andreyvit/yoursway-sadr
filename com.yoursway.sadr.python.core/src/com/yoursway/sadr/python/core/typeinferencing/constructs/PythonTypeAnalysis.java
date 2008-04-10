package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.runtime.PythonMethod;
import com.yoursway.sadr.python.core.runtime.requestors.methods.MethodRequestor;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;
import com.yoursway.sadr.python.core.typeinferencing.goals.CallableReturnValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.types.InstanceType;
import com.yoursway.sadr.python.core.typeinferencing.types.SimpleType;
import com.yoursway.sadr.python.core.typeinferencing.types.Type;

/**
 * Encapsulates all functionality specific for type analysis on Python
 * constructs.
 */
public class PythonTypeAnalysis implements AnalysisProvider {
    
    private final class BinaryOperationHandlerImplementation implements BinaryOperationHandler {
        private final ValueInfo types = null;
        private final List<ValueInfoGoal> goals = new ArrayList<ValueInfoGoal>();
        private final ValueInfoGoal leftGoal;
        private final ValueInfoGoal rightGoal;
        
        public BinaryOperationHandlerImplementation(ValueInfoGoal leftGoal, ValueInfoGoal rightGoal) {
            this.leftGoal = leftGoal;
            this.rightGoal = rightGoal;
        }
        
        public void setContext(PythonConstructImpl<BinaryExpression> context, final ValueInfo leftInfo,
                final ValueInfo rightInfo) {
            final StandardTypes builtins = context.staticContext().builtins();
            final ValueInfoBuilder builder = new ValueInfoBuilder();
            
            final ValueInfoBuilder leftNonHandledTypes = new ValueInfoBuilder();
            
            for (final Type left : leftInfo.containedTypes()) {
                final boolean[] modFound = new boolean[] { false };
                
                if (left.equals(builtins.stringType()))
                    builder.add(new SimpleType(builtins.stringType()));
                else if (left instanceof InstanceType) {
                    ((InstanceType) left).findMethod("__mod__", new MethodRequestor() {
                        public void accept(PythonMethod method) {
                            ValueInfo leftArgInfo = new ValueInfoBuilder().add(left).build();
                            modFound[0] = true;
                            ValueInfoGoal g = new CallableReturnValueInfoGoal(leftArgInfo, method,
                                    new ValueInfo[] { leftArgInfo, rightInfo }, InfoKind.TYPE);
                            
                            goals.add(g);
                        }
                    });
                    if (!modFound[0])
                        leftNonHandledTypes.add(left);
                }
            }
            
            if (!leftNonHandledTypes.isEmpty()) {
                for (final Type right : rightInfo.containedTypes()) {
                    if (right instanceof InstanceType) {
                        right.findMethod("__rmod__", new MethodRequestor() {
                            public void accept(PythonMethod method) {
                                ValueInfo rightArgInfo = new ValueInfoBuilder().add(right).build();
                                ValueInfoGoal g = new CallableReturnValueInfoGoal(rightArgInfo, method,
                                        new ValueInfo[] { rightArgInfo, leftNonHandledTypes.build() },
                                        InfoKind.TYPE);
                                
                                goals.add(g);
                            }
                        });
                    }
                }
            }
        }
        
        public void provideSubgoals(SubgoalRequestor requestor) {
            requestor.subgoal(leftGoal);
            requestor.subgoal(rightGoal);
        }
        
        public void done(ContinuationScheduler requestor) {
            ValueInfo leftInfo = leftGoal.result(null);
            ValueInfo rightInfo = rightGoal.result(null);
            
            /*
             * ValueInfoBuilder vib = new ValueInfoBuilder(); for (ValueInfoGoal
             * g : goals) vib.addResultOf(g, null); types = vib.build();
             */
        }
        
        public ValueInfo result() {
            assert !types.isEmpty() : "Trying to call result() on non-executed goal";
            return types;
        }
    }
    
    public BinaryOperationHandler getBinaryPercentHandler(ValueInfoGoal leftGoal, ValueInfoGoal rightGoal) {
        return new BinaryOperationHandlerImplementation(leftGoal, rightGoal);
    }
}
