package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.PythonClassDefinition;
import com.yoursway.sadr.python.core.runtime.PythonSourceClassDefinition;
import com.yoursway.sadr.python.core.runtime.PythonUtils;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.ModelAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.ModelRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.ClassScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class ClassDeclarationC extends PythonConstructImpl<PythonClassDeclaration> implements ModelAffector {
    
    private final ClassScope innerContext;
    
    ClassDeclarationC(PythonStaticContext sc, PythonClassDeclaration node) {
        super(sc, node);
        String name = node.getName();
        innerContext = lookupScope(staticContext().classLookup().lookupClass(name));
    }
    
    private ClassScope lookupScope(PythonClass klass) {
        ClassScope scope = null;
        for (PythonClassDefinition definition : klass.getDefinitions())
            if (definition instanceof PythonSourceClassDefinition) {
                PythonSourceClassDefinition def = (PythonSourceClassDefinition) definition;
                if (node == def.node()) {
                    scope = def.scope();
                }
            }
        if (scope == null)
            scope = new ClassScope((Scope) staticContext(), this, klass);
        return scope;
    }
    
    public void evaluateValue(PythonDynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            ValueInfoContinuation continuation) {
        continuation.consume(emptyValueInfo(), requestor);
    }
    
    @Override
    protected PythonStaticContext innerContext() {
        return innerContext;
    }
    
    public void actOnModel(ModelRequest request) {
        String superclassName = PythonUtils.superclassName(node);
        PythonClass superclass = null;
        if (superclassName != null)
            superclass = staticContext().classLookup().lookupClass(superclassName);
        
        new PythonSourceClassDefinition(innerContext, request.context(), this, superclass);
    }
    
    public String displayName() {
        return node.getName();
    }
    
}
