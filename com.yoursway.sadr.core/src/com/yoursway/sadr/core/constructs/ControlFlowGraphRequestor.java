/**
 * 
 */
package com.yoursway.sadr.core.constructs;

import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;

public interface ControlFlowGraphRequestor<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    ContinuationRequestorCalledToken process(ControlFlowGraph<C, SC, DC, N> effectiveGraph,
            ContinuationScheduler requestor);
    
}
