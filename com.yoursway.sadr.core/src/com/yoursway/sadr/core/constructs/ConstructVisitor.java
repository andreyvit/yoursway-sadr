package com.yoursway.sadr.core.constructs;

import com.yoursway.sadr.engine.ContinuationRequestor;

public interface ConstructVisitor<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    void enter(C construct, ContinuationRequestor requestor, VisitorRequestor<C, SC, DC, N> continuation);
    
    void leave();
    
}
