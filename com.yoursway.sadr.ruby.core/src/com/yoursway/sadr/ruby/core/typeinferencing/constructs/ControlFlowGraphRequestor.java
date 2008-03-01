/**
 * 
 */
package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import com.yoursway.sadr.engine.ContinuationRequestor;

public interface ControlFlowGraphRequestor {
    
    void process(ControlFlowGraph effectiveGraph, ContinuationRequestor requestor);
    
}
