package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.PythonSourceClassDefinition;
import com.yoursway.sadr.python.core.runtime.PythonUtils;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.ModelAffector;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.ModelRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class ClassDeclarationC extends PythonConstructImpl<PythonClassDeclaration> implements ModelAffector {
    
    ClassDeclarationC(Scope sc, PythonClassDeclaration node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
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
