package com.yoursway.sadr.core.constructs;

import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;

public interface VisitorRequestor<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    ContinuationRequestorCalledToken consume(ConstructVisitor<C, SC, DC, N> visitor,
            ContinuationScheduler requestor);
    
}
