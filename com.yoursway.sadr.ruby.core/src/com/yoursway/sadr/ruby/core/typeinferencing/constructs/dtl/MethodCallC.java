package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.ruby.core.runtime.requestors.methods.CollectingMethodRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.IndexAffector;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.IndexRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;

public class MethodCallC extends CallC implements IndexAffector {
    
    MethodCallC(RubyStaticContext sc, CallExpression node) {
        super(sc, node);
    }
    
    public void evaluateValue(final RubyDynamicContext dc, final InfoKind infoKind,
            ContinuationRequestor requestor, final ValueInfoContinuation continuation) {
        final RubyConstruct receiver = wrap(innerContext(), node.getReceiver());
        final String name = node.getName();
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
        //! request.addMethodCall(node.getMethodName(), this);
    }
    
}
