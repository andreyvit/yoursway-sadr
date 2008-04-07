package com.yoursway.sadr.core.constructs;

import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.SimpleContinuation;

public class ConstructControlFlowTraverser<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N>
        extends AbstractConstructTraverser<C, SC, DC, N> {
    
    @Override
    protected ContinuationRequestorCalledToken traverseChildrenOf(C construct,
            final ContinuationScheduler requestor, final ConstructVisitor<C, SC, DC, N> visitor,
            final boolean callLeave, final SimpleContinuation afterTraverse,
            final ConstructVisitor<C, SC, DC, N> subvisitor) {
        return construct.calculateEffectiveControlFlowGraph(requestor,
                new ControlFlowGraphRequestor<C, SC, DC, N>() {
                    
                    public ContinuationRequestorCalledToken process(
                            ControlFlowGraph<C, SC, DC, N> effectiveGraph, ContinuationScheduler requestor) {
                        return traverseSpecificChildren(effectiveGraph.getNodes(), visitor, subvisitor,
                                callLeave, requestor, afterTraverse);
                    }
                    
                });
    }
    
}
