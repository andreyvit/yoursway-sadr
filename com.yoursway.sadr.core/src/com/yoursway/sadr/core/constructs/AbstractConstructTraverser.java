package com.yoursway.sadr.core.constructs;

import java.util.List;

import kilim.pausable;

public abstract class AbstractConstructTraverser<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    @pausable
    public void traverse(C construct, ConstructVisitor<C, SC, DC, N> visitor) {
        traverse(construct, visitor, true);
    }
    
    @pausable
    public void traverse(final C construct, final ConstructVisitor<C, SC, DC, N> visitor,
            final boolean callLeave) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        ConstructVisitor<C, SC, DC, N> subvisitor = visitor.enter(construct);
        if (subvisitor != null)
            traverseChildrenOf(construct, visitor, callLeave, subvisitor);
    }
    
    @pausable
    protected abstract void traverseChildrenOf(C construct, final ConstructVisitor<C, SC, DC, N> visitor,
            final boolean callLeave, final ConstructVisitor<C, SC, DC, N> subvisitor);
    
    @pausable
    protected void traverseSpecificChildren(List<C> nodes, final ConstructVisitor<C, SC, DC, N> visitor,
            final ConstructVisitor<C, SC, DC, N> subvisitor, final boolean callLeave) {
        boolean callLeaveOnChildren = (subvisitor != visitor);
        for (C node : nodes)
            traverse(node, subvisitor, callLeaveOnChildren);
        if (callLeave)
            subvisitor.leave();
    }
    
}
