package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.RubyClass;
import com.yoursway.sadr.python.core.runtime.RubyMethod;
import com.yoursway.sadr.python.core.runtime.RubyProcedure;
import com.yoursway.sadr.python.core.runtime.RubySourceMethod;
import com.yoursway.sadr.python.core.runtime.RubySourceProcedure;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.ModelAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.ModelRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.LocalScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.MethodScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.ProcedureScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class MethodDeclarationC extends PythonConstructImpl<MethodDeclaration> implements ModelAffector {
    
    private LocalScope innerScope;
    
    MethodDeclarationC(PythonStaticContext sc, MethodDeclaration node) {
        super(sc, node);
        String name = node.getName();
        RubyClass klass = staticContext().currentClass();
        if (klass != null) {
            RubyMethod method = klass.findMethod(name);
            if (method instanceof RubySourceMethod)
                innerScope = ((RubySourceMethod) method).scope();
        } else {
            RubyProcedure procedure = staticContext().procedureLookup().findProcedure(name);
            if (procedure instanceof RubySourceProcedure)
                innerScope = ((RubySourceProcedure) procedure).scope();
        }
    }
    
    public void evaluateValue(PythonDynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        continuation.consume(emptyValueInfo(), requestor);
    }
    
    @Override
    protected PythonStaticContext innerContext() {
        if (innerScope == null)
            throw new IllegalStateException("innerContext called before the model has been built");
        return innerScope;
    }
    
    public void actOnModel(ModelRequest request) {
        RubyClass klass = staticContext().currentClass();
        if (klass != null) {
            RubySourceMethod method = new RubySourceMethod(klass, request.context(), this);
            innerScope = new MethodScope((Scope) staticContext(), method, node);
        } else {
            RubySourceProcedure procedure = new RubySourceProcedure(request.context(), this);
            innerScope = new ProcedureScope((Scope) staticContext(), procedure, node);
        }
    }
    
    public LocalScope methodScope() {
        return innerScope;
    }
    
}
