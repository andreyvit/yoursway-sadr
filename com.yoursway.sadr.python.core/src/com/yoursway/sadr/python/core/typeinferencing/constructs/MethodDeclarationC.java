package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;

import java.util.List;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class MethodDeclarationC extends PythonConstructImpl<MethodDeclaration> implements Scope {
    
    private final List<PythonConstruct> children;
    
    MethodDeclarationC(Scope sc, MethodDeclaration node) {
        super(sc, node);
        
        children = wrapEnclosedChildren(this);
        
        //        String name = node.getName();
        //        PythonClass klass = staticContext().currentClass();
        //        if (klass != null) {
        //            PythonMethod method = klass.findMethod(name);
        //            if (method instanceof PythonSourceMethod)
        //                innerScope = ((PythonSourceMethod) method).scope();
        //        } else {
        //            PythonProcedure procedure = staticContext().procedureLookup().findProcedure(name);
        //            if (procedure instanceof PythonSourceProcedure)
        //                innerScope = ((PythonSourceProcedure) procedure).scope();
        //        }
    }
    
    private List<PythonConstruct> wrapEnclosedChildren(Scope innerScope) {
        return PythonConstructFactory.wrap(this, node.getBody().getChilds());
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
    public List<PythonConstruct> getEnclosedconstructs() {
        return children;
    }
    
    //    public void actOnModel(ModelRequest request) {
    //        PythonClass klass = staticContext().currentClass();
    //        if (klass != null) {
    //            PythonSourceMethod method = new PythonSourceMethod(klass, request.context(), this);
    //            innerScope = new MethodScope(nearestScope(), method, node);
    //        } else {
    //            PythonSourceProcedure procedure = new PythonSourceProcedure(request.context(), this);
    //            innerScope = new ProcedureScope(nearestScope(), procedure, node);
    //        }
    //    }
}
