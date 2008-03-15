package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.runtime.requestors.methods.CollectingMethodRequestor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexRequest;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;

public class MethodCallC extends CallC implements IndexAffector {
    
    MethodCallC(PythonStaticContext sc, PythonCallExpression node) {
        super(sc, node);
    }
    
    public void evaluateValue(final PythonDynamicContext dc, final InfoKind infoKind,
            ContinuationRequestor requestor, final ValueInfoContinuation continuation) {
        final PythonConstruct receiver = wrap(innerContext(), node.getReceiver());
        final String name = node.getMethodName();
        requestor.subgoal(new Continuation() {
            
            final ValueInfoGoal recvGoal = new ExpressionValueInfoGoal(receiver, dc, infoKind);
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                requestor.subgoal(recvGoal);
            }
            
            public void done(ContinuationRequestor requestor) {
                CollectingMethodRequestor rq = new CollectingMethodRequestor();
                recvGoal.result(null).findMethod(name, rq);
                if (rq.anythingFound())
                    requestor.subgoal(new CallablesReturnTypeCont(infoKind, arguments(), dc, rq.asArray(),
                            recvGoal.result(null), continuation));
                else
                    continuation.consume(emptyValueInfo(), requestor);
            }
            
        });
        
    }
    
    public void actOnIndex(IndexRequest request) {
        request.addMethodCall(node.getMethodName(), this);
    }
    
}
