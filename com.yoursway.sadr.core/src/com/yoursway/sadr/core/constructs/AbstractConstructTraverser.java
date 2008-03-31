package com.yoursway.sadr.core.constructs;

import java.util.List;

import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.Continuations;
import com.yoursway.sadr.engine.IterationContinuation;
import com.yoursway.sadr.engine.SimpleContinuation;

public abstract class AbstractConstructTraverser<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    public ContinuationRequestorCalledToken traverse(C construct, ContinuationScheduler requestor,
            ConstructVisitor<C, SC, DC, N> visitor, SimpleContinuation continuation) {
        return traverse(construct, requestor, visitor, true, continuation);
    }
    
    public ContinuationRequestorCalledToken traverse(final C construct,
            final ContinuationScheduler requestor, final ConstructVisitor<C, SC, DC, N> visitor,
            final boolean callLeave, final SimpleContinuation afterTraverse) {
        return visitor.enter(construct, requestor, new VisitorRequestor<C, SC, DC, N>() {
            
            public ContinuationRequestorCalledToken consume(ConstructVisitor<C, SC, DC, N> subvisitor,
                    ContinuationScheduler requestor) {
                if (subvisitor == null)
                    return afterTraverse.run(requestor);
                else {
                    return traverseChildrenOf(construct, requestor, visitor, callLeave, afterTraverse,
                            subvisitor);
                }
                
            }
        });
        
    }
    
    protected abstract ContinuationRequestorCalledToken traverseChildrenOf(C construct,
            final ContinuationScheduler requestor, final ConstructVisitor<C, SC, DC, N> visitor,
            final boolean callLeave, final SimpleContinuation afterTraverse,
            final ConstructVisitor<C, SC, DC, N> subvisitor);
    
    protected ContinuationRequestorCalledToken traverseSpecificChildren(List<C> nodes,
            final ConstructVisitor<C, SC, DC, N> visitor, final ConstructVisitor<C, SC, DC, N> subvisitor,
            final boolean callLeave, ContinuationScheduler requestor, final SimpleContinuation afterTraverse) {
        final boolean callLeaveOnChildren = (subvisitor != visitor);
        return Continuations.iterate(nodes, new IterationContinuation<C>() {
            
            public ContinuationRequestorCalledToken iteration(C value, ContinuationScheduler requestor,
                    SimpleContinuation continuation) {
                return traverse(value, requestor, subvisitor, callLeaveOnChildren, continuation);
            }
            
        }, requestor, new SimpleContinuation() {
            
            public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
                if (callLeave)
                    subvisitor.leave();
                return afterTraverse.run(requestor);
            }
            
        });
    }
    
}
