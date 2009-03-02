package com.yoursway.sadr.core.constructs;

import kilim.pausable;

public class ConstructControlFlowTraverser<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N>
        extends AbstractConstructTraverser<C, SC, DC, N> {
    
    @Override
    @pausable
    protected void traverseChildrenOf(C construct, final ConstructVisitor<C, SC, DC, N> visitor,
            final boolean callLeave, final ConstructVisitor<C, SC, DC, N> subvisitor) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        ControlFlowGraph<C, SC, DC, N> cfg = construct.calculateEffectiveControlFlowGraph();
        traverseSpecificChildren(cfg.getNodes(), visitor, subvisitor, callLeave);
    }
    
}
