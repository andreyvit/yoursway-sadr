package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.python.core.runtime.PythonUtils;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.EvalRequest;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class ProcedureCallC extends CallC {
    
    ProcedureCallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
    }
    
    //    private ContinuationRequestorCalledToken classNewInstance(PythonClassType klass, PythonDynamicContext dc,
    //            InfoKind infoKind, ContinuationScheduler requestor, ValueInfoContinuation continuation) {
    //        Value value = new InstanceValue(klass, staticContext().instanceRegistrar());
    //        SingleTypeSet ts = TypeSetFactory.typeSetWith(new InstanceType(klass));
    //        PythonMethod[] methods = TypeUtils.findMethodsByPrefix(ts, "__new__");
    //        if (methods.length > 0) {
    //            PythonMethod method = methods[0];
    //            return requestor.schedule(new CallablesReturnTypeCont(infoKind, arguments(), dc,
    //                    new Callable[] { method }, null, continuation));
    //        } else {
    //            ValueInfo result = createResult(ts, valueSetWith(value));
    //            return continuation.consume(result, requestor);
    //        }
    //    }
    //    
    //    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
    //            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
    //        final String name = node.getProcedureName();
    //        if (name != null) {
    //            PythonClassType klass = staticContext().classLookup().findClass(name);
    //            if (klass != null)
    //                return classNewInstance(klass, dc, infoKind, requestor, continuation);
    //        }
    //        
    //        Callable procedure = staticContext().procedureLookup().findProcedure(name);
    //        if (procedure != null)
    //            return requestor.schedule(new CallablesReturnTypeCont(infoKind, arguments(), dc,
    //                    new Callable[] { procedure }, null, continuation));
    //        else
    //            return continuation.consume(emptyValueInfo(), requestor);
    //    }
    //    
    //    @Override
    //    public ContinuationRequestorCalledToken calculateEffectiveControlFlowGraph(
    //            ContinuationScheduler requestor,
    //            ControlFlowGraphRequestor<PythonConstruct, Scope, PythonDynamicContext, ASTNode> continuation) {
    //        if (node.getProcedureName().equalsIgnoreCase("eval")) {
    //            List<PythonConstruct> constructs = filter(enclosedConstructs(), NOT_METHOD);
    //            for (ModuleDeclaration root : staticContext().extentionsOf(node))
    //                constructs.add(new EvalRootC(staticContext(), root));
    //            return continuation.process(
    //                    new ControlFlowGraph<PythonConstruct, Scope, PythonDynamicContext, ASTNode>(constructs),
    //                    requestor);
    //        } else {
    //            return super.calculateEffectiveControlFlowGraph(requestor, continuation);
    //        }
    //    }
    //    
    public void actOnIndex(IndexRequest request) {
        request.addProcedureCall(node.getName(), this);
    }
    
    public void actOnEval(EvalRequest request) {
        ASTNode args = node.getArgs();
        List<ASTNode> children = PythonUtils.childrenOf(args);
        if (node.getName().equals("eval") && children.size() > 0) {
            request.addEval(this, subconstructFor(children.get(0)));
        }
    }
    
    @Override
    public String toString() {
        return node.getName() + "()";
    }
    
    @Override
    public PythonConstruct getReceiver() {
        return null;
    }
}
