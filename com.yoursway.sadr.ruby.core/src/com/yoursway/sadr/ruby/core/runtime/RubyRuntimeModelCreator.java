package com.yoursway.sadr.ruby.core.runtime;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.ruby.core.runtime.contributions.Context;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.ModelRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.FileScope;

public class RubyRuntimeModelCreator {
    
    public void process(Context context, RubyConstruct root, ContinuationRequestor requestor,
            SimpleContinuation continuation) {
        FileScope fileScope = context.fileScope();
        ModelRequest request = new ModelRequest(context);
        fileScope.propagationTracker().traverseStatically(root, request, requestor, continuation);
    }
    
}
