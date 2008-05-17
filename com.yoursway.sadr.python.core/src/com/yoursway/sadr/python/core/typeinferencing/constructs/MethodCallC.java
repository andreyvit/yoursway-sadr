package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.python.core.typeinferencing.constructs.Effects.NO_EFFECTS;
import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.CallF;
import com.yoursway.sadr.python_v2.goals.FieldReadF;

public class MethodCallC extends CallC implements IndexAffector {
    
    private final PythonConstruct receiver;
    
    MethodCallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
        receiver = wrap(node.getReceiver());
    }
    
    //    public ContinuationRequestorCalledToken evaluateValue(final PythonDynamicContext dc,
    //            final InfoKind infoKind, ContinuationScheduler requestor, final ValueInfoContinuation continuation) {
    //        final PythonConstruct receiver = wrap(innerContext(), node.getReceiver());
    //        final String name = node.getMethodName();
    //        return requestor.schedule(new Continuation() {
    //            
    //            final ValueInfoGoal recvGoal = new ExpressionValueInfoGoal(receiver, dc, infoKind);
    //            
    //            public Goal[] provideSubgoals() {
    //                return new Goal[] { recvGoal };
    //            }
    //            
    //            public void done(ContinuationScheduler requestor) {
    //                CollectingMethodRequestor rq = new CollectingMethodRequestor();
    //                ValueInfoUtils.findMethod(recvGoal.result(null), name, rq);
    //                if (rq.anythingFound())
    //                    requestor.schedule(new CallablesReturnTypeCont(infoKind, arguments(), dc, rq.asArray(),
    //                            recvGoal.result(null), continuation));
    //                else
    //                    continuation.consume(emptyValueInfo(), requestor);
    //            }
    //            
    //        });
    //        
    //    }
    
    public void actOnIndex(IndexRequest request) {
        request.addMethodCall(node.getName(), this);
    }
    
    @Override
    public String toString() {
        return node.getReceiver() + "." + node.getName() + "()";
    }
    
    @Override
    public PythonConstruct getReceiver() {
        return receiver;
    }
    
    @Override
    public Frog toFrog() {
        List<Frog> argFrogs = new ArrayList<Frog>();
        for (PythonConstruct construct : getArgs()) {
            argFrogs.add(construct.toFrog());
        }
        return new CallF(new FieldReadF(receiver.toFrog(), node.getName()), argFrogs);
    }
    
    @Override
    public Effects getEffects() {
        return new Effects(NO_EFFECTS, singleton(toFrog()));
    }
    
}
