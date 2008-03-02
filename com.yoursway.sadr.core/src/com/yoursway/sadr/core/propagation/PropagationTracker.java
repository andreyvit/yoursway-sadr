package com.yoursway.sadr.core.propagation;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.core.constructs.DynamicContext;
import com.yoursway.sadr.core.constructs.IConstruct;
import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.StaticContext;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.SimpleContinuation;

public interface PropagationTracker<C extends IConstruct<?, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    void initiateBackwardPropagation(Goal goal, ContinuationRequestor requestor,
            ValueInfoContinuation continuation);
    
    void traverseEntirely(C construct, Request<C, SC, DC, N> request, ContinuationRequestor requestor,
            SimpleContinuation continuation);
    
}
