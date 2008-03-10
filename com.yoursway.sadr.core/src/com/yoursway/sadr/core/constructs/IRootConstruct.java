package com.yoursway.sadr.core.constructs;

public interface IRootConstruct<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N>
        extends IConstruct<C, SC, DC, N> {
    
    SC innerStaticContext();
    
}
