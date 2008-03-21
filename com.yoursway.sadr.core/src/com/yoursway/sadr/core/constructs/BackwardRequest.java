package com.yoursway.sadr.core.constructs;

public interface BackwardRequest<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    void visit(C construct);
    
    boolean done();
    
}
