package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.engine.util.Lists.filter;
import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.List;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.Callable;
import com.yoursway.sadr.python.core.runtime.RubyUtils;
import com.yoursway.sadr.python.core.typeinferencing.constructs.ControlFlowGraph;
import com.yoursway.sadr.python.core.typeinferencing.constructs.ControlFlowGraphRequestor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.DynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.StaticContext;
import com.yoursway.sadr.python.core.typeinferencing.engine.ValueInfoContinuation;

public class ProcedureCallC extends CallC {
    
    ProcedureCallC(StaticContext sc, CallExpression node) {
        super(sc, node);
    }
    
    public void evaluateValue(DynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        final String name = node.getName();
        Callable procedure = staticContext().procedureLookup().findProcedure(name);
        if (procedure != null)
            requestor.subgoal(new CallablesReturnTypeCont(infoKind, RubyUtils.argumentsOf(node),
                    new Callable[] { procedure }, null, continuation));
        else
            continuation.consume(emptyValueInfo(), requestor);
    }
    
    @Override
    public void calculateEffectiveControlFlowGraph(ContinuationRequestor requestor,
            ControlFlowGraphRequestor continuation) {
        if (node.getName().equalsIgnoreCase("eval")) {
            List<IConstruct> constructs = filter(enclosedConstructs(), NOT_METHOD);
            for (ModuleDeclaration root : staticContext().extentionsOf(node))
                constructs.add(new EvalRootC(staticContext(), root));
            continuation.process(new ControlFlowGraph(constructs), requestor);
        } else {
            super.calculateEffectiveControlFlowGraph(requestor, continuation);
        }
    }
    
}
