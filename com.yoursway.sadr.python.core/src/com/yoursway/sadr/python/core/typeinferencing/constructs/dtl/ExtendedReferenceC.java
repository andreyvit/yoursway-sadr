package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.python.parser.ast.expressions.ExtendedVariableReference;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;

public class ExtendedReferenceC extends PythonConstructImpl<ExtendedVariableReference> {
    
    ExtendedReferenceC(PythonStaticContext sc, ExtendedVariableReference node) {
        super(sc, node);
    }
    
    public void evaluateValue(final PythonDynamicContext dc, final InfoKind infoKind,
            ContinuationRequestor requestor, final ValueInfoContinuation continuation) {
        continuation.consume(emptyValueInfo(), requestor);
        
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
