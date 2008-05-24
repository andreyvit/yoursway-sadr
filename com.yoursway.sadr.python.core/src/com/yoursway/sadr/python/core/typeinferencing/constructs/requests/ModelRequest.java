package com.yoursway.sadr.python.core.typeinferencing.constructs.requests;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.VisitorRequestor;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonDynamicContext;
import com.yoursway.sadr.python_v2.model.Context;

public class ModelRequest implements
        Request<PythonConstruct, Scope, PythonDynamicContext, ASTNode> {
    
    private final Context context;
    
    public ModelRequest(Context context) {
        this.context = context;
    }
    
    public Context context() {
        return context;
    }
    
    public ContinuationRequestorCalledToken enter(PythonConstruct construct, ContinuationScheduler requestor,
            VisitorRequestor<PythonConstruct, Scope, PythonDynamicContext, ASTNode> continuation) {
        if (construct instanceof ModelAffector)
            ((ModelAffector) construct).actOnModel(this);
        return continuation.consume(this, requestor);
    }
    
    public void leave() {
    }
    
}
