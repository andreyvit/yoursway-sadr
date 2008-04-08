package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ruby.ast.RubyClassDeclaration;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.runtime.RubyClassDefinition;
import com.yoursway.sadr.ruby.core.runtime.RubySourceClassDefinition;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.ModelAffector;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.ModelRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.ClassScope;

public class ClassDeclarationC extends RubyConstructImpl<RubyClassDeclaration> implements ModelAffector {
    
    private final ClassScope innerContext;
    
    ClassDeclarationC(RubyStaticContext sc, RubyClassDeclaration node) {
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
            scope = new ClassScope(nearestScope(), this, klass);
        return scope;
    }
    
    public ContinuationRequestorCalledToken evaluateValue(RubyDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
    @Override
    protected RubyStaticContext innerContext() {
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
