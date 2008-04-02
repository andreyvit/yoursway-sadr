package com.yoursway.sadr.python.core.runtime;

import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.ModelRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;

public class PythonRuntimeModelCreator {
    
    public ContinuationRequestorCalledToken process(Context context, PythonConstruct root,
            ContinuationScheduler requestor, SimpleContinuation continuation) {
        FileScope fileScope = context.fileScope();
        ModelRequest request = new ModelRequest(context);
        return fileScope.propagationTracker().traverseStatically(root, request, requestor, continuation);
    }
    
}
