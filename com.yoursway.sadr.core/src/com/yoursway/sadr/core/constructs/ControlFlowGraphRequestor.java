/**
 * 
 */
package com.yoursway.sadr.core.constructs;

import com.yoursway.sadr.engine.ContinuationRequestor;

public interface ControlFlowGraphRequestor<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    void process(ControlFlowGraph<C, SC, DC, N> effectiveGraph, ContinuationRequestor requestor);
    
}
