package com.yoursway.sadr.core.constructs;

public interface Request<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N>
        extends ConstructVisitor<C, SC, DC, N> {
    
}
