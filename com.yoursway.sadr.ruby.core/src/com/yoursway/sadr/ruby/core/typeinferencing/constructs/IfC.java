package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ruby.ast.RubyIfStatement;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.core.constructs.ControlFlowGraph;
import com.yoursway.sadr.core.constructs.ControlFlowGraphRequestor;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.values.Value;
import com.yoursway.sadr.ruby.core.typeinferencing.values.ValueTraits;
import com.yoursway.sadr.ruby.core.typeinferencing.valuesets.ValueSet;

public class IfC extends RubyConstructImpl<RubyIfStatement> {
    
    IfC(RubyStaticContext sc, RubyIfStatement node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(RubyDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
    @Override
    public ContinuationRequestorCalledToken calculateEffectiveControlFlowGraph(
            ContinuationScheduler requestor,
            final ControlFlowGraphRequestor<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> continuation) {
        final RubyConstruct condition = wrap(innerContext(), node.getCondition());
        return requestor.schedule(new Continuation() {
            
            ValueInfoGoal conditionGoal = new ExpressionValueInfoGoal(condition, new EmptyDynamicContext(),
                    InfoKind.VALUE);
            
            public Goal[] provideSubgoals() {
                return new Goal[] { conditionGoal };
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
                List<RubyConstruct> staticNodes = new ArrayList<RubyConstruct>();
                staticNodes.add(condition);
                if (visitThen)
                    staticNodes.add(wrap(staticContext(), node.getThen()));
                if (visitElse)
                    staticNodes.add(wrap(staticContext(), node.getElse()));
                continuation.process(ControlFlowGraph.create(staticNodes), requestor);
            }
            
        });
    }
}
