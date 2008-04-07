package com.yoursway.sadr.ruby.core.runtime;

import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.ruby.core.runtime.contributions.Context;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.ModelRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.FileScope;

public class RubyRuntimeModelCreator {
    
    public ContinuationRequestorCalledToken process(Context context, RubyConstruct root,
            ContinuationScheduler requestor, SimpleContinuation continuation) {
        FileScope fileScope = context.fileScope();
        ModelRequest request = new ModelRequest(context);
        return fileScope.propagationTracker().traverseStatically(root, request, requestor, continuation);
    }
    
}
