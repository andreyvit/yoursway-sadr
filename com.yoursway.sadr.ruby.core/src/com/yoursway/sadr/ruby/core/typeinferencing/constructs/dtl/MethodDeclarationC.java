package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.runtime.RubyMethod;
import com.yoursway.sadr.ruby.core.runtime.RubyProcedure;
import com.yoursway.sadr.ruby.core.runtime.RubySourceMethod;
import com.yoursway.sadr.ruby.core.runtime.RubySourceProcedure;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.ModelAffector;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.ModelRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.LocalScope;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.MethodScope;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.ProcedureScope;

public class MethodDeclarationC extends DtlConstruct<MethodDeclaration> implements ModelAffector {
    
    private LocalScope innerScope;
    
    MethodDeclarationC(RubyStaticContext sc, MethodDeclaration node) {
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
    
    public ContinuationRequestorCalledToken evaluateValue(RubyDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(ValueInfo.emptyValueInfo(), requestor);
    }
    
    @Override
    protected RubyStaticContext innerContext() {
        if (innerScope == null)
            throw new IllegalStateException("innerContext called before the model has been built");
        return innerScope;
    }
    
    public void actOnModel(ModelRequest request) {
        RubyClass klass = staticContext().currentClass();
        if (klass != null) {
            RubySourceMethod method = new RubySourceMethod(klass, request.context(), this);
            innerScope = new MethodScope(nearestScope(), method, node);
        } else {
            RubySourceProcedure procedure = new RubySourceProcedure(request.context(), this);
            innerScope = new ProcedureScope(nearestScope(), procedure, node);
        }
    }
    
    public LocalScope methodScope() {
        return innerScope;
    }
    
}
