package com.yoursway.sadr.core.propagation;

import kilim.pausable;

import com.yoursway.sadr.core.constructs.AbstractConstructTraverser;
import com.yoursway.sadr.core.constructs.ConstructControlFlowTraverser;
import com.yoursway.sadr.core.constructs.ConstructStaticStructureTraverser;
import com.yoursway.sadr.core.constructs.DynamicContext;
import com.yoursway.sadr.core.constructs.IConstruct;
import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.StaticContext;

public class PropagationTrackerImpl<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N>
        implements PropagationTracker<C, SC, DC, N> {
    
    public static <C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> PropagationTracker<C, SC, DC, N> create() {
        return new PropagationTrackerImpl<C, SC, DC, N>();
    }
    
    @pausable
    public void traverseEntirely(C construct, final Request<C, SC, DC, N> request) {
        AbstractConstructTraverser<C, SC, DC, N> traverser = new ConstructControlFlowTraverser<C, SC, DC, N>();
        traverser.traverse(construct, request);
    }
    
    @pausable
    public void traverseStatically(C construct, final Request<C, SC, DC, N> request) {
        AbstractConstructTraverser<C, SC, DC, N> traverser = new ConstructStaticStructureTraverser<C, SC, DC, N>();
        traverser.traverse(construct, request);
    }
    
}
