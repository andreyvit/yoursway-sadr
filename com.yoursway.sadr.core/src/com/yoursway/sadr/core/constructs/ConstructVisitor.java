package com.yoursway.sadr.core.constructs;

import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;

public interface ConstructVisitor<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    ContinuationRequestorCalledToken enter(C construct, ContinuationScheduler requestor,
            VisitorRequestor<C, SC, DC, N> continuation);
    
    void leave();
    
}
