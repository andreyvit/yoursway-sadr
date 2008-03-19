package com.yoursway.sadr.core.constructs;

public interface CfgCursor<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    C current();
    
    boolean isBof();
    
    boolean isEof();
    
    CfgCursor<C, SC, DC, N> previous();
    
    CfgCursor<C, SC, DC, N> next();
    
}
