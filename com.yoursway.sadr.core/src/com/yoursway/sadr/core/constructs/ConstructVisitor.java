package com.yoursway.sadr.core.constructs;

import kilim.pausable;

public interface ConstructVisitor<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    @pausable
    ConstructVisitor<C, SC, DC, N> enter(C construct);
    
    void leave();
    
}
