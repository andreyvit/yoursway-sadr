package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.PythonMethod;
import com.yoursway.sadr.python.core.runtime.PythonProcedure;
import com.yoursway.sadr.python.core.runtime.PythonSourceMethod;
import com.yoursway.sadr.python.core.runtime.PythonSourceProcedure;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.ModelAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.ModelRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.LocalScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.MethodScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.ProcedureScope;

public class MethodDeclarationC extends PythonConstructImpl<MethodDeclaration> implements ModelAffector {
    
    private LocalScope innerScope;
    
    MethodDeclarationC(PythonStaticContext sc, MethodDeclaration node) {
        super(sc, node);
        String name = node.getName();
        PythonClass klass = staticContext().currentClass();
        if (klass != null) {
            PythonMethod method = klass.findMethod(name);
            if (method instanceof PythonSourceMethod)
                innerScope = ((PythonSourceMethod) method).scope();
        } else {
            PythonProcedure procedure = staticContext().procedureLookup().findProcedure(name);
            if (procedure instanceof PythonSourceProcedure)
                innerScope = ((PythonSourceProcedure) procedure).scope();
        }
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
    @Override
    protected PythonStaticContext innerContext() {
        if (innerScope == null)
            throw new IllegalStateException("innerContext called before the model has been built");
        return innerScope;
    }
    
    public void actOnModel(ModelRequest request) {
        PythonClass klass = staticContext().currentClass();
        if (klass != null) {
            PythonSourceMethod method = new PythonSourceMethod(klass, request.context(), this);
            innerScope = new MethodScope(nearestScope(), method, node);
        } else {
            PythonSourceProcedure procedure = new PythonSourceProcedure(request.context(), this);
            innerScope = new ProcedureScope(nearestScope(), procedure, node);
        }
    }
    
    public LocalScope methodScope() {
        return innerScope;
    }
    
}
