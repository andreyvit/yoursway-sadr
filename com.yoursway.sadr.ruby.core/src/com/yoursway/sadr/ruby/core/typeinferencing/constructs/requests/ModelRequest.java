package com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.VisitorRequestor;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.ruby.core.runtime.contributions.Context;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;

public class ModelRequest implements Request<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> {
    
    private final Context context;
    
    public ModelRequest(Context context) {
        this.context = context;
    }
    
    public Context context() {
        return context;
    }
    
    public void enter(RubyConstruct construct, ContinuationRequestor requestor,
            VisitorRequestor<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> continuation) {
        if (construct instanceof ModelAffector)
            ((ModelAffector) construct).actOnModel(this);
        continuation.consume(this, requestor);
    }
    
    public void leave() {
    }
    
}
