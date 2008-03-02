/**
 * 
 */
package com.yoursway.sadr.core.constructs;

import java.util.List;

public class ControlFlowGraph<C extends IConstruct<?, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    private final List<C> constructs;
    
    public ControlFlowGraph(List<C> constructs) {
        this.constructs = constructs;
    }
    
    public List<C> getNodes() {
        return constructs;
    }
    
}
