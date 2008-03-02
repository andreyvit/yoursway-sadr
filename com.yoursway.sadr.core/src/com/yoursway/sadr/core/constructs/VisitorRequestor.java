package com.yoursway.sadr.core.constructs;

import com.yoursway.sadr.engine.ContinuationRequestor;

public interface VisitorRequestor<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    void consume(ConstructVisitor<C, SC, DC, N> visitor, ContinuationRequestor requestor);
    
}
