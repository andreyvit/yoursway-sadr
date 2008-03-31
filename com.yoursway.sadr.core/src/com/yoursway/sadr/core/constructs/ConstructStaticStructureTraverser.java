package com.yoursway.sadr.core.constructs;

import java.util.List;

import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.SimpleContinuation;

public class ConstructStaticStructureTraverser<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N>
        extends AbstractConstructTraverser<C, SC, DC, N> {
    
    @Override
    protected ContinuationRequestorCalledToken traverseChildrenOf(C construct,
            final ContinuationScheduler requestor, final ConstructVisitor<C, SC, DC, N> visitor,
            final boolean callLeave, final SimpleContinuation afterTraverse,
            final ConstructVisitor<C, SC, DC, N> subvisitor) {
        List<C> subconstructs = construct.enclosedConstructs();
        return traverseSpecificChildren(subconstructs, visitor, subvisitor, callLeave, requestor,
                afterTraverse);
    }
    
}
