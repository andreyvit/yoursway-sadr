package com.yoursway.sadr.core.constructs;

import java.util.List;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.SimpleContinuation;

public class ConstructStaticStructureTraverser<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N>
        extends AbstractConstructTraverser<C, SC, DC, N> {
    
    @Override
    protected void traverseChildrenOf(C construct, final ContinuationRequestor requestor,
            final ConstructVisitor<C, SC, DC, N> visitor, final boolean callLeave,
            final SimpleContinuation afterTraverse, final ConstructVisitor<C, SC, DC, N> subvisitor) {
        List<C> subconstructs = construct.enclosedConstructs();
        traverseSpecificChildren(subconstructs, visitor, subvisitor, callLeave, requestor, afterTraverse);
    }
    
}
