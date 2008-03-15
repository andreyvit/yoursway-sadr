package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonArrayAccessExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;

public class ArrayAccessC extends PythonConstructImpl<PythonArrayAccessExpression> {
    
    ArrayAccessC(PythonStaticContext sc, PythonArrayAccessExpression node) {
        super(sc, node);
    }
    
    public void evaluateValue(final PythonDynamicContext dc, final InfoKind infoKind,
            ContinuationRequestor requestor, final ValueInfoContinuation continuation) {
        final Expression array = node.getArray();
        requestor.subgoal(new Continuation() {
            
            private final ValueInfoGoal nameGoal = new ExpressionValueInfoGoal(subconstructFor(array), dc,
                    infoKind);
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                requestor.subgoal(nameGoal);
            }
            
            public void done(ContinuationRequestor requestor) {
                continuation.consume(nameGoal.result(null).unwrapArray(), requestor);
            }
            
        });
    }
}
