package com.yoursway.sadr.core.constructs;

public interface Request<C extends IConstruct<?, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    void accept(C construct);
    
}
