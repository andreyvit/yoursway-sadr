package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.engine.util.Lists.filter;
import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.core.constructs.ControlFlowGraph;
import com.yoursway.sadr.core.constructs.ControlFlowGraphRequestor;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.Callable;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexRequest;

public class ProcedureCallC extends CallC implements IndexAffector {
    
    ProcedureCallC(PythonStaticContext sc, PythonCallExpression node) {
        super(sc, node);
    }
    
    public void evaluateValue(PythonDynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        final String name = node.getProcedureName();
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
            ControlFlowGraphRequestor<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> continuation) {
        if (node.getProcedureName().equalsIgnoreCase("eval")) {
            List<PythonConstruct> constructs = filter(enclosedConstructs(), NOT_METHOD);
            for (ModuleDeclaration root : staticContext().extentionsOf(node))
                constructs.add(new EvalRootC(staticContext(), root));
            continuation
                    .process(
                            new ControlFlowGraph<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode>(
                                    constructs), requestor);
        } else {
            super.calculateEffectiveControlFlowGraph(requestor, continuation);
        }
    }
    
    public void actOnIndex(IndexRequest request) {
        request.addProcedureCall(node.getProcedureName(), this);
    }
    
}
