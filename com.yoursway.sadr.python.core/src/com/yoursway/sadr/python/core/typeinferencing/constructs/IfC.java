package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.statements.IfStatement;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.core.constructs.ControlFlowGraph;
import com.yoursway.sadr.core.constructs.ControlFlowGraphRequestor;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.values.ValueTraits;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSet;

public class IfC extends PythonConstructImpl<IfStatement> {
    
    IfC(PythonStaticContext sc, IfStatement node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
    @Override
    public ContinuationRequestorCalledToken calculateEffectiveControlFlowGraph(
            ContinuationScheduler requestor,
            final ControlFlowGraphRequestor<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> continuation) {
        final PythonConstruct condition = wrap(innerContext(), node.getCondition());
        return requestor.schedule(new Continuation() {
            
            ValueInfoGoal conditionGoal = new ExpressionValueInfoGoal(condition, new EmptyDynamicContext(),
                    InfoKind.VALUE);
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                requestor.subgoal(conditionGoal);
            }
            
            public void done(ContinuationScheduler requestor) {
                ValueSet valueSet = conditionGoal.result(null).getValueSet();
                boolean visitThen = false, visitElse = false;
                for (Value value : valueSet.containedValues()) {
                    ValueTraits tr = value.traits();
                    if (tr.cohersibleToBoolean())
                        if (tr.coherseToBoolean())
                            visitThen = true;
                        else
                            visitElse = true;
                }
                if (!visitThen && !visitElse)
                    visitThen = visitElse = true;
                List<PythonConstruct> staticNodes = new ArrayList<PythonConstruct>();
                staticNodes.add(condition);
                if (visitThen)
                    staticNodes.add(wrap(innerContext(), node.getThen()));
                if (visitElse)
                    staticNodes.add(wrap(innerContext(), node.getElse()));
                continuation.process(ControlFlowGraph.create(staticNodes), requestor);
            }
            
        });
    }
}
