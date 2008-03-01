package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.runtime.requestors.methods.CollectingMethodRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.DynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.StaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.ValueInfoContinuation;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public class MethodCallC extends CallC {
    
    MethodCallC(StaticContext sc, CallExpression node) {
        super(sc, node);
    }
    
    public void evaluateValue(final DynamicContext dc, final InfoKind infoKind,
            ContinuationRequestor requestor, final ValueInfoContinuation continuation) {
        final ASTNode receiver = node.getReceiver();
        final String name = node.getName();
        requestor.subgoal(new Continuation() {
            
            final ValueInfoGoal recvGoal = new ExpressionValueInfoGoal((Scope) dc, receiver, infoKind);
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                requestor.subgoal(recvGoal);
            }
            
            public void done(ContinuationRequestor requestor) {
                CollectingMethodRequestor rq = new CollectingMethodRequestor();
                recvGoal.result(null).findMethod(name, rq);
                if (rq.anythingFound())
                    requestor.subgoal(new CallablesReturnTypeCont(infoKind, RubyUtils.argumentsOf(node), rq
                            .asArray(), recvGoal.result(null), continuation));
                else
                    continuation.consume(emptyValueInfo(), requestor);
            }
            
        });
        
    }
}
