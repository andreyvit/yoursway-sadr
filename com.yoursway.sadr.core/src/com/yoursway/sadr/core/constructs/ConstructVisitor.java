package com.yoursway.sadr.core.constructs;

public interface ConstructVisitor<C extends IConstruct<?, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    ConstructVisitor<C, SC, DC, N> enter(C construct);
    
    void leave();
    
}
