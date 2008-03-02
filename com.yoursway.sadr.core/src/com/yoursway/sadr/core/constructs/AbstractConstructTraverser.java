package com.yoursway.sadr.core.constructs;

import java.util.List;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Continuations;
import com.yoursway.sadr.engine.IterationContinuation;
import com.yoursway.sadr.engine.SimpleContinuation;

public abstract class AbstractConstructTraverser<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    public void traverse(C construct, ContinuationRequestor requestor,
            ConstructVisitor<C, SC, DC, N> visitor, SimpleContinuation continuation) {
        traverse(construct, requestor, visitor, true, continuation);
    }
    
    public void traverse(final C construct, final ContinuationRequestor requestor,
            final ConstructVisitor<C, SC, DC, N> visitor, final boolean callLeave,
            final SimpleContinuation afterTraverse) {
        visitor.enter(construct, requestor, new VisitorRequestor<C, SC, DC, N>() {
            
            public void consume(ConstructVisitor<C, SC, DC, N> subvisitor, ContinuationRequestor requestor) {
                if (subvisitor == null)
                    afterTraverse.run(requestor);
                else {
                    traverseChildrenOf(construct, requestor, visitor, callLeave, afterTraverse, subvisitor);
                }
                
            }
        });
        
    }
    
    protected abstract void traverseChildrenOf(C construct, final ContinuationRequestor requestor,
            final ConstructVisitor<C, SC, DC, N> visitor, final boolean callLeave,
            final SimpleContinuation afterTraverse, final ConstructVisitor<C, SC, DC, N> subvisitor);
    
    protected void traverseSpecificChildren(List<C> nodes, final ConstructVisitor<C, SC, DC, N> visitor,
            final ConstructVisitor<C, SC, DC, N> subvisitor, final boolean callLeave,
            ContinuationRequestor requestor, final SimpleContinuation afterTraverse) {
        final boolean callLeaveOnChildren = (subvisitor != visitor);
        Continuations.iterate(nodes, new IterationContinuation<C>() {
            
            public void iteration(C value, ContinuationRequestor requestor, SimpleContinuation continuation) {
                traverse(value, requestor, subvisitor, callLeaveOnChildren, continuation);
            }
            
        }, requestor, new SimpleContinuation() {
            
            public void run(ContinuationRequestor requestor) {
                if (callLeave)
                    subvisitor.leave();
                afterTraverse.run(requestor);
            }
            
        });
    }
    
}
