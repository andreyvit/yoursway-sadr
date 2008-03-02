package com.yoursway.sadr.core.constructs;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.SimpleContinuation;

public class ConstructControlFlowTraverser<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N>
        extends AbstractConstructTraverser<C, SC, DC, N> {
    
    @Override
    protected void traverseChildrenOf(C construct, final ContinuationRequestor requestor,
            final ConstructVisitor<C, SC, DC, N> visitor, final boolean callLeave,
            final SimpleContinuation afterTraverse, final ConstructVisitor<C, SC, DC, N> subvisitor) {
        construct.calculateEffectiveControlFlowGraph(requestor,
                new ControlFlowGraphRequestor<C, SC, DC, N>() {
                    
                    public void process(ControlFlowGraph<C, SC, DC, N> effectiveGraph,
                            ContinuationRequestor requestor) {
                        traverseSpecificChildren(effectiveGraph.getNodes(), visitor, subvisitor, callLeave,
                                requestor, afterTraverse);
                    }
                    
                });
    }
    
}
