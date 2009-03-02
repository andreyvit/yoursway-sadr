package com.yoursway.sadr.core.constructs;

import java.util.List;

import kilim.pausable;

public class ConstructStaticStructureTraverser<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N>
        extends AbstractConstructTraverser<C, SC, DC, N> {
    
    @Override
    @pausable
    protected void traverseChildrenOf(C construct, final ConstructVisitor<C, SC, DC, N> visitor,
            final boolean callLeave, final ConstructVisitor<C, SC, DC, N> subvisitor) {
        List<C> subconstructs = construct.enclosedConstructs();
        traverseSpecificChildren(subconstructs, visitor, subvisitor, callLeave);
    }
    
}
