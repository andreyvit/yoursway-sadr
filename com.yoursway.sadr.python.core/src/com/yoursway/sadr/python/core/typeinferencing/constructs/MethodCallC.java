package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.requestors.methods.CollectingMethodRequestor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexRequest;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoUtils;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class MethodCallC extends CallC implements IndexAffector {
    
    MethodCallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(final PythonDynamicContext dc,
            final InfoKind infoKind, ContinuationScheduler requestor, final ValueInfoContinuation continuation) {
        final PythonConstruct receiver = wrap(innerContext(), node.getReceiver());
        final String name = node.getMethodName();
        return requestor.schedule(new Continuation() {
            
            final ValueInfoGoal recvGoal = new ExpressionValueInfoGoal(receiver, dc, infoKind);
            
            public Goal[] provideSubgoals() {
                return new Goal[] { recvGoal };
            }
            
            public void done(ContinuationScheduler requestor) {
                CollectingMethodRequestor rq = new CollectingMethodRequestor();
                ValueInfoUtils.findMethod(recvGoal.result(null), name, rq);
                if (rq.anythingFound())
                    requestor.schedule(new CallablesReturnTypeCont(infoKind, arguments(), dc, rq.asArray(),
                            recvGoal.result(null), continuation));
                else
                    continuation.consume(emptyValueInfo(), requestor);
            }
            
        });
        
    }
    
    public void actOnIndex(IndexRequest request) {
        request.addMethodCall(node.getMethodName(), this);
    }
    
    @Override
    public String toString() {
        return node.getReceiver() + "." + node.getMethodName() + "()";
    }
}
