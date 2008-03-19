package com.yoursway.sadr.core.constructs;

import com.yoursway.sadr.core.propagation.PropagationTracker;

public interface StaticContext<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    PropagationTracker<C, SC, DC, N> propagationTracker();
    
    C parentConstruct();
    
}
