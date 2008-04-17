package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.simple_types.SimpleTypeItem;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.runtime.PythonMethod;
import com.yoursway.sadr.python.core.runtime.requestors.methods.MethodRequestor;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;
import com.yoursway.sadr.python.core.typeinferencing.goals.CallableReturnValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoUtils;
import com.yoursway.sadr.python.core.typeinferencing.types.InstanceType;
import com.yoursway.sadr.python.core.typeinferencing.types.TypeUtils;

/**
 * Encapsulates all functionality specific for type analysis on Python
 * constructs.
 */
public class PythonTypeAnalysis implements AnalysisProvider {
    
    private final class BinaryOperationHandlerImplementation implements BinaryOperationHandler {
        private ValueInfo types = null;
        private final List<ValueInfoGoal> goals = new ArrayList<ValueInfoGoal>();
        private ValueInfoGoal leftGoal;
        private ValueInfoGoal rightGoal;
        
        public BinaryOperationHandlerImplementation() {
        }
        
        public void setContext(PythonConstructImpl<BinaryExpression> context, final ValueInfo leftInfo,
                final ValueInfo rightInfo) {
            final StandardTypes builtins = context.staticContext().builtins();
            final ValueInfoBuilder builder = new ValueInfoBuilder();
            
            final ValueInfoBuilder leftNonHandledTypes = new ValueInfoBuilder();
            
            for (final Type left : leftInfo.containedTypes()) {
                final boolean[] modFound = new boolean[] { false };
                
                if (left.equals(builtins.stringType()))
                    builder.add(new SimpleTypeItem(builtins.stringType()));
                else if (left instanceof InstanceType) {
                    TypeUtils.findMethod(left, "__mod__", new MethodRequestor() {
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
                        TypeUtils.findMethod(right, "__rmod__", new MethodRequestor() {
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
            ValueInfoBuilder vib = new ValueInfoBuilder();
            for (ValueInfoGoal g : goals)
                ValueInfoUtils.addResultOf(vib, g, null);
            types = vib.build();
            
        }
        
        public ValueInfo result() {
            assert !types.isEmpty() : "Trying to call result() on non-executed goal";
            return types;
        }
        
        public void setContext(PythonConstructImpl<BinaryExpression> context, ValueInfoGoal leftGoal,
                ValueInfoGoal rightGoal) {
            this.leftGoal = leftGoal;
            this.rightGoal = rightGoal;
        }
    }
    
    public BinaryOperationHandler getBinaryPercentHandler() {
        return new BinaryOperationHandlerImplementation();
    }
}
