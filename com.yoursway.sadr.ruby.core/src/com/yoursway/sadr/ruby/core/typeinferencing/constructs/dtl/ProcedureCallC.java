package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.engine.util.Lists.filter;
import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.List;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.core.constructs.ControlFlowGraphRequestor;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.Callable;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;

public class ProcedureCallC extends CallC {
    
    ProcedureCallC(RubyStaticContext sc, CallExpression node) {
        super(sc, node);
    }
    
    public void evaluateValue(RubyDynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        final String name = node.getName();
        Callable procedure = rubyStaticContext().procedureLookup().findProcedure(name);
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
            List<RubyConstruct> rubyConstructs = filter(enclosedConstructs(), NOT_METHOD);
            for (ModuleDeclaration root : rubyStaticContext().extentionsOf(node))
                rubyConstructs.add(new EvalRootC(rubyStaticContext(), root));
            continuation.process(new ControlFlowGraph(rubyConstructs), requestor);
        } else {
            super.calculateEffectiveControlFlowGraph(requestor, continuation);
        }
    }
    
}
