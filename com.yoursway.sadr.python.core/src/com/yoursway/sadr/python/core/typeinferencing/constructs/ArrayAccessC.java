package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.google.common.collect.Iterators.transform;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonArrayAccessExpression;

import com.google.common.base.Function;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoUtils;

public class ArrayAccessC extends PythonConstructImpl<PythonArrayAccessExpression> {
    
    private static final Function<MumblaWumblaThreesome, MumblaWumblaThreesome> WRAP_INTO_ARRAY = new Function<MumblaWumblaThreesome, MumblaWumblaThreesome>() {
        
        public MumblaWumblaThreesome apply(MumblaWumblaThreesome from) {
            return from.wrapIntoArray();
        }
        
    };
    
    ArrayAccessC(PythonStaticContext sc, PythonArrayAccessExpression node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(final PythonDynamicContext dc,
            final InfoKind infoKind, ContinuationScheduler requestor, final ValueInfoContinuation continuation) {
        final Expression array = node.getArray();
        return requestor.schedule(new Continuation() {
            
            private final ValueInfoGoal nameGoal = new ExpressionValueInfoGoal(subconstructFor(array), dc,
                    infoKind);
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                requestor.subgoal(nameGoal);
            }
            
            public void done(ContinuationScheduler requestor) {
                continuation.consume(ValueInfoUtils.unwrapArray(nameGoal.result(null)), requestor);
            }
            
        });
    }
    
    public PythonConstruct array() {
        return wrap(innerContext(), node.getArray());
    }
    
    @Override
    public Collection<MumblaWumblaThreesome> mumblaWumbla() {
        return newArrayList(transform(array().mumblaWumbla().iterator(), WRAP_INTO_ARRAY));
    }
    
}
