/**
 * 
 */
package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.List;

public class ControlFlowGraph {
    
    private final List<IConstruct> constructs;
    
    public ControlFlowGraph(List<IConstruct> constructs) {
        this.constructs = constructs;
    }
    
    public List<IConstruct> getNodes() {
        return constructs;
    }
    
}