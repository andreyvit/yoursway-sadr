package com.yoursway.sadr.core.propagation;

import kilim.pausable;

import com.yoursway.sadr.core.constructs.DynamicContext;
import com.yoursway.sadr.core.constructs.IConstruct;
import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.StaticContext;

public interface PropagationTracker<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    @pausable
    void traverseEntirely(C construct, Request<C, SC, DC, N> request);
    
    @pausable
    void traverseStatically(C construct, Request<C, SC, DC, N> request);
    
}
