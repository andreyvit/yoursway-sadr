package com.yoursway.sadr.python.core.runtime;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.rq.ModelRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;

public class RubyRuntimeModelCreator {
    
    public void process(Context context, PythonConstruct root, ContinuationRequestor requestor,
            SimpleContinuation continuation) {
        FileScope fileScope = context.fileScope();
        ModelRequest request = new ModelRequest(context);
        fileScope.propagationTracker().traverseStatically(root, request, requestor, continuation);
    }
    
}
