package com.yoursway.sadr.core.propagation;

import com.yoursway.sadr.core.constructs.AbstractConstructTraverser;
import com.yoursway.sadr.core.constructs.ConstructControlFlowTraverser;
import com.yoursway.sadr.core.constructs.ConstructStaticStructureTraverser;
import com.yoursway.sadr.core.constructs.DynamicContext;
import com.yoursway.sadr.core.constructs.IConstruct;
import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.StaticContext;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.SimpleContinuation;

public class PropagationTrackerImpl<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N>
        implements PropagationTracker<C, SC, DC, N> {
    
    public static <C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> PropagationTracker<C, SC, DC, N> create() {
        return new PropagationTrackerImpl<C, SC, DC, N>();
    }
    
    public ContinuationRequestorCalledToken traverseEntirely(C construct,
            final Request<C, SC, DC, N> request, ContinuationScheduler requestor,
            SimpleContinuation continuation) {
        AbstractConstructTraverser<C, SC, DC, N> traverser = new ConstructControlFlowTraverser<C, SC, DC, N>();
        return traverser.traverse(construct, requestor, request, continuation);
    }
    
    public ContinuationRequestorCalledToken traverseStatically(C construct,
            final Request<C, SC, DC, N> request, ContinuationScheduler requestor,
            SimpleContinuation continuation) {
        AbstractConstructTraverser<C, SC, DC, N> traverser = new ConstructStaticStructureTraverser<C, SC, DC, N>();
        return traverser.traverse(construct, requestor, request, continuation);
    }
    
}
