package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.engine.util.Lists.filter;
import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.createResult;
import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;
import static com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSetFactory.valueSetWith;

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
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.PythonMethod;
import com.yoursway.sadr.python.core.runtime.PythonUtils;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.EvalRequest;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.EvalsAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexRequest;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.types.ClassType;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetFactory;
import com.yoursway.sadr.python.core.typeinferencing.typesets.internal.SingleTypeSet;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;
import com.yoursway.sadr.python.core.typeinferencing.values.Value;

public class ProcedureCallC extends CallC implements IndexAffector, EvalsAffector {
    
    ProcedureCallC(PythonStaticContext sc, PythonCallExpression node) {
        super(sc, node);
    }
    
    private void classNewInstance(PythonClass klass, PythonDynamicContext dc, InfoKind infoKind,
            ContinuationRequestor requestor, ValueInfoContinuation continuation) {
        Value value = new InstanceValue(klass, staticContext().instanceRegistrar());
        SingleTypeSet ts = TypeSetFactory.typeSetWith(new ClassType(klass));
        PythonMethod[] methods = ts.findMethodsByPrefix("__new__");
        if (methods.length > 0) {
            PythonMethod method = methods[0];
            requestor.subgoal(new CallablesReturnTypeCont(infoKind, arguments(), dc,
                    new Callable[] { method }, null, continuation));
        } else {
            ValueInfo result = createResult(ts, valueSetWith(value));
            continuation.consume(result, requestor);
        }
    }
    
    public void evaluateValue(PythonDynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        final String name = node.getProcedureName();
        if (name != null) {
            PythonClass klass = staticContext().classLookup().findClass(name);
            if (klass != null) {
                classNewInstance(klass, dc, infoKind, requestor, continuation);
                return;
            }
        }
        
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
    
    public void actOnEval(EvalRequest request) {
        ASTNode args = node.getArgs();
        List<ASTNode> children = PythonUtils.childrenOf(args);
        if (node.getProcedureName().equals("eval") && children.size() > 0) {
            request.addEval(this, subconstructFor(children.get(0)));
        }
    }
    
}
