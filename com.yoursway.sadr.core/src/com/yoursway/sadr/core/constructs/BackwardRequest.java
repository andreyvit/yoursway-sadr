package com.yoursway.sadr.core.constructs;

import kilim.pausable;

public interface BackwardRequest<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    @pausable
    void visit(C construct);
    
    boolean done();
    
}
