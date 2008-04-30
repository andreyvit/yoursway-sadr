package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class BinaryPercentC extends BinaryC {
    
    protected AnalysisProvider typeAnalysisProvider;
    
    BinaryPercentC(Scope sc, BinaryExpression node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(final PythonDynamicContext dc,
            final InfoKind infoKind, ContinuationScheduler scheduler, final ValueInfoContinuation continuation) {
        final PythonConstruct leftArg = wrap(innerContext(), node.getLeft());
        final PythonConstruct rightArg = wrap(innerContext(), node.getRight());
        return scheduler.schedule(new Continuation() {
            
            private final ValueInfoGoal leftGoal = new ExpressionValueInfoGoal(leftArg, dc, infoKind);
            
            private final ValueInfoGoal rightGoal = new ExpressionValueInfoGoal(rightArg, dc, infoKind);
            
            private BinaryOperationHandler binaryPercentHandler;
            
            public Goal[] provideSubgoals() {
                typeAnalysisProvider = new PythonTypeAnalysis();
                binaryPercentHandler = typeAnalysisProvider.getBinaryPercentHandler();
                binaryPercentHandler.setContext(BinaryPercentC.this, leftGoal, rightGoal);
                return binaryPercentHandler.provideSubgoals();
            }
            
            public void done(ContinuationScheduler requestor) {
                binaryPercentHandler.done(requestor);
                continuation.consume(binaryPercentHandler.result(), requestor);
            }
        });
    }
}
