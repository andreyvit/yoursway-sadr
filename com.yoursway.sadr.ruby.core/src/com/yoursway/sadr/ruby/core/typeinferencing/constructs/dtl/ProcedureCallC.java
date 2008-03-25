package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.engine.util.Lists.filter;
import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.core.constructs.ControlFlowGraph;
import com.yoursway.sadr.core.constructs.ControlFlowGraphRequestor;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.Callable;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.EvalRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.EvalsAffector;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.IndexRequest;

public class ProcedureCallC extends CallC implements EvalsAffector {
    
    ProcedureCallC(RubyStaticContext sc, CallExpression node) {
        super(sc, node);
    }
    
    public void evaluateValue(RubyDynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        final String name = node.getName();
        Callable procedure = staticContext().procedureLookup().findProcedure(name);
        if (procedure != null)
            requestor.subgoal(new CallablesReturnTypeCont(infoKind, arguments(), dc,
                    new Callable[] { procedure }, null, continuation));
        else
            continuation.consume(emptyValueInfo(), requestor);
    }
    
    @Override
    public void calculateEffectiveControlFlowGraph(
            ContinuationRequestor requestor,
            ControlFlowGraphRequestor<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> continuation) {
        if (node.getName().equalsIgnoreCase("eval")) {
            List<RubyConstruct> constructs = filter(enclosedConstructs(), NOT_METHOD);
            for (ModuleDeclaration root : staticContext().extentionsOf(node))
                constructs.add(new EvalRootC(staticContext(), root));
            continuation.process(
                    new ControlFlowGraph<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode>(
                            constructs), requestor);
        } else {
            super.calculateEffectiveControlFlowGraph(requestor, continuation);
        }
    }
    
    public void actOnIndex(IndexRequest request) {
        request.addProcedureCall(node.getName(), this);
    }
    
    public void actOnEval(EvalRequest request) {
        ASTNode args = node.getArgs();
        List<ASTNode> children = RubyUtils.childrenOf(args);
        if (node.getName().equals("eval") && children.size() > 0) {
            request.addEval(this, subconstructFor(children.get(0)));
        }
    }
    
}
