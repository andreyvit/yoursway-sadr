package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.python.parser.ast.statements.IfStatement;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class IfC extends PythonConstructImpl<IfStatement> {
    
    IfC(Scope sc, IfStatement node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
    //    @Override
    //    public ContinuationRequestorCalledToken calculateEffectiveControlFlowGraph(
    //            ContinuationScheduler requestor,
    //            final ControlFlowGraphRequestor<PythonConstruct, Scope, PythonDynamicContext, ASTNode> continuation) {
    //        final PythonConstruct condition = wrap(innerContext(), node.getCondition());
    //        return requestor.schedule(new Continuation() {
    //            
    //            ValueInfoGoal conditionGoal = new ExpressionValueInfoGoal(condition, new EmptyDynamicContext(),
    //                    InfoKind.VALUE);
    //            
    //            public Goal[] provideSubgoals() {
    //                return new Goal[] { conditionGoal };
    //            }
    //            
    //            public void done(ContinuationScheduler requestor) {
    //                ValueSet valueSet = conditionGoal.result(null).getValueSet();
    //                boolean visitThen = false, visitElse = false;
    //                for (Value value : valueSet.containedValues()) {
    //                    ValueTraits tr = value.traits();
    //                    if (tr.cohersibleToBoolean())
    //                        if (tr.coherseToBoolean())
    //                            visitThen = true;
    //                        else
    //                            visitElse = true;
    //                }
    //                if (!visitThen && !visitElse)
    //                    visitThen = visitElse = true;
    //                List<PythonConstruct> staticNodes = new ArrayList<PythonConstruct>();
    //                staticNodes.add(condition);
    //                if (visitThen)
    //                    staticNodes.add(wrap(innerContext(), node.getThen()));
    //                if (visitElse)
    //                    staticNodes.add(wrap(innerContext(), node.getElse()));
    //                continuation.process(ControlFlowGraph.create(staticNodes), requestor);
    //            }
    //            
    //        });
    //    }
}
