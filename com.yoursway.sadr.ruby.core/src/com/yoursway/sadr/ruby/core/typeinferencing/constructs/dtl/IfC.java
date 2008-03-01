package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ruby.ast.RubyIfStatement;

import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.ControlFlowGraph;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.ControlFlowGraphRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.DynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.IConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.StaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.ValueInfoContinuation;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.ruby.core.typeinferencing.values.Value;
import com.yoursway.sadr.ruby.core.typeinferencing.values.ValueTraits;
import com.yoursway.sadr.ruby.core.typeinferencing.valuesets.ValueSet;

public class IfC extends DtlConstruct<RubyIfStatement> {
    
    IfC(StaticContext sc, RubyIfStatement node) {
        super(sc, node);
    }
    
    public void evaluateValue(DynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        continuation.consume(emptyValueInfo(), requestor);
    }
    
    @Override
    public void calculateEffectiveControlFlowGraph(ContinuationRequestor requestor,
            final ControlFlowGraphRequestor continuation) {
        final ASTNode condition = node.getCondition();
        requestor.subgoal(new Continuation() {
            
            ValueInfoGoal conditionGoal = new ExpressionValueInfoGoal((Scope) staticContext(), condition,
                    InfoKind.VALUE);
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                requestor.subgoal(conditionGoal);
            }
            
            public void done(ContinuationRequestor requestor) {
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
                List<IConstruct> staticNodes = new ArrayList<IConstruct>();
                staticNodes.add(wrap(staticContext(), condition));
                if (visitThen)
                    staticNodes.add(wrap(staticContext(), node.getThen()));
                if (visitElse)
                    staticNodes.add(wrap(staticContext(), node.getElse()));
                continuation.process(new ControlFlowGraph(staticNodes), requestor);
            }
            
        });
    }
}
