package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.blocks.foundation.values.ValueTraits;
import com.yoursway.sadr.blocks.simple_types.SimpleTypeItem;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;

public class PythonValueAnalysis implements AnalysisProvider {
    
    public BinaryOperationHandler getBinaryPercentHandler() {
        return new BinaryOperationHandler() {
            private PythonConstructImpl<BinaryExpression> context;
            private ValueInfoGoal leftGoal;
            private ValueInfoGoal rightGoal;
            private ValueInfo leftInfo;
            private ValueInfo rightInfo;
            
            public ValueInfo result() {
                leftInfo = leftGoal.result(null);
                rightInfo = rightGoal.result(null);
                final StandardTypes builtins = context.staticContext().builtins();
                final ValueInfoBuilder builder = new ValueInfoBuilder();
                builder.add(new SimpleTypeItem(builtins.stringType()));
                
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
            
            public void setContext(PythonConstructImpl<BinaryExpression> context, ValueInfoGoal leftGoal,
                    ValueInfoGoal rightGoal) {
                this.context = context;
                this.leftGoal = leftGoal;
                this.rightGoal = rightGoal;
                
            }
            
            public void done(ContinuationScheduler requestor) {
            }
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                // TODO Auto-generated method stub
                
            }
        };
    }
}