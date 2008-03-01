package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import org.eclipse.dltk.ruby.ast.RubyArrayAccessExpression;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.constructs.DynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.StaticContext;
import com.yoursway.sadr.python.core.typeinferencing.engine.ValueInfoContinuation;

public class ArrayAccessC extends PythonConstruct<RubyArrayAccessExpression> {
    
    ArrayAccessC(StaticContext sc, RubyArrayAccessExpression node) {
        super(sc, node);
    }
    
    public void evaluateValue(final DynamicContext dc, final InfoKind infoKind,
            ContinuationRequestor requestor, final ValueInfoContinuation continuation) {
        //        
        //        final Statement name = node.getName();
        //        requestor.subgoal(new Continuation() {
        //            
        //            private final ValueInfoGoal nameGoal = new ExpressionValueInfoGoal((Scope) dc, name, infoKind);
        //            
        //            public void provideSubgoals(SubgoalRequestor requestor) {
        //                requestor.subgoal(nameGoal);
        //            }
        //            
        //            public void done(ContinuationRequestor requestor) {
        //                continuation.consume(nameGoal.result(null).unwrapArray(), requestor);
        //            }
        //            
        //        });
    }
    
}
