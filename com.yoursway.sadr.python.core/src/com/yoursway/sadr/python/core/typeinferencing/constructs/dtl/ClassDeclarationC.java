package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.RubyClass;
import com.yoursway.sadr.python.core.runtime.RubyClassDefinition;
import com.yoursway.sadr.python.core.runtime.RubySourceClassDefinition;
import com.yoursway.sadr.python.core.runtime.RubyUtils;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.ModelAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.ModelRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.ClassScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class ClassDeclarationC extends PythonConstructImpl<PythonClassDeclaration> implements ModelAffector {
    
    private final ClassScope innerContext;
    
    ClassDeclarationC(PythonStaticContext sc, PythonClassDeclaration node) {
        super(sc, node);
        String name = node.getName();
        innerContext = lookupScope(staticContext().classLookup().lookupClass(name));
    }
    
    private ClassScope lookupScope(RubyClass klass) {
        ClassScope scope = null;
        for (RubyClassDefinition definition : klass.getDefinitions())
            if (definition instanceof RubySourceClassDefinition) {
                RubySourceClassDefinition def = (RubySourceClassDefinition) definition;
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
        String superclassName = RubyUtils.superclassName(node);
        RubyClass superclass = null;
        if (superclassName != null)
            superclass = staticContext().classLookup().lookupClass(superclassName);
        
        new RubySourceClassDefinition(innerContext, request.context(), this, superclass);
    }
    
    public String displayName() {
        return node.getName();
    }
    
}
