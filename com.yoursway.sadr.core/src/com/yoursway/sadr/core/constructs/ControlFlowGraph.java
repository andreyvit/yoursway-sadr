/**
 * 
 */
package com.yoursway.sadr.core.constructs;

import java.util.List;

public class ControlFlowGraph<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    private final List<C> constructs;
    
    public static <C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> ControlFlowGraph<C, SC, DC, N> create(
            List<C> constructs) {
        return new ControlFlowGraph<C, SC, DC, N>(constructs);
    }
    
    public ControlFlowGraph(List<C> constructs) {
        this.constructs = constructs;
    }
    
    public List<C> getNodes() {
        return constructs;
    }
    
}
