package com.yoursway.sadr.core.propagation;

import com.yoursway.sadr.core.constructs.DynamicContext;
import com.yoursway.sadr.core.constructs.IConstruct;
import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.StaticContext;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.SimpleContinuation;

public interface PropagationTracker<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    ContinuationRequestorCalledToken traverseEntirely(C construct, Request<C, SC, DC, N> request,
            ContinuationScheduler requestor, SimpleContinuation continuation);
    
    ContinuationRequestorCalledToken traverseStatically(C construct, Request<C, SC, DC, N> request,
            ContinuationScheduler requestor, SimpleContinuation continuation);
    
}
