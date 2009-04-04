package com.yoursway.sadr.core.constructs;

import java.util.List;

import kilim.pausable;

import com.yoursway.sadr.core.IValueInfo;

public interface IConstruct<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    SC staticContext();
    
    N node();
    
    /**
     * WARNING: this method has O(N) complexity (where N is the number of
     * nodes), so use sparingly.
     */
    C subconstructFor(N node);
    
    List<C> enclosedConstructs();
    
    @pausable
    IValueInfo evaluateValue(DC dc);
    
    @pausable
    ControlFlowGraph<C, SC, DC, N> calculateEffectiveControlFlowGraph();
    
}
