package com.yoursway.sadr.core.constructs;

import java.util.Iterator;
import java.util.List;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.SimpleContinuation;

public class ConstructControlFlowTraverser<C extends IConstruct<?, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    public void traverse(C construct, ContinuationRequestor requestor,
            ConstructVisitor<C, SC, DC, N> visitor, SimpleContinuation continuation) {
        traverse(construct, requestor, visitor, true, continuation);
    }
    
    public void traverse(C construct, final ContinuationRequestor requestor,
            final ConstructVisitor<C, SC, DC, N> visitor, final boolean callLeave,
            final SimpleContinuation afterTraverse) {
        final ConstructVisitor<C, SC, DC, N> subvisitor = visitor.enter(construct);
        if (subvisitor == null)
            afterTraverse.run(requestor);
        else {
            construct.calculateEffectiveControlFlowGraph(requestor,
                    new ControlFlowGraphRequestor<C, SC, DC, N>() {
                        
                        public void process(ControlFlowGraph<C, SC, DC, N> effectiveGraph,
                                ContinuationRequestor requestor) {
                            boolean callLeaveOnChildren = (subvisitor != visitor);
                            List<C> nodes = effectiveGraph.getNodes();
                            Iterator<C> iterator = nodes.iterator();
                            traverseSubnodes(requestor, subvisitor, callLeaveOnChildren, iterator,
                                    new SimpleContinuation() {
                                        
                                        public void run(ContinuationRequestor requestor) {
                                            if (callLeave)
                                                subvisitor.leave();
                                            afterTraverse.run(requestor);
                                        }
                                        
                                    });
                        }
                        
                        private void traverseSubnodes(final ContinuationRequestor requestor,
                                final ConstructVisitor<C, SC, DC, N> visitor,
                                final boolean callLeaveOnChildren, final Iterator<C> iterator,
                                final SimpleContinuation afterTraverseChildren) {
                            if (iterator.hasNext()) {
                                C subnode = iterator.next();
                                traverse(subnode, requestor, visitor, callLeaveOnChildren,
                                        new SimpleContinuation() {
                                            
                                            public void run(ContinuationRequestor requestor) {
                                                traverseSubnodes(requestor, visitor, callLeaveOnChildren,
                                                        iterator, afterTraverseChildren);
                                            }
                                            
                                        });
                            } else {
                                afterTraverseChildren.run(requestor);
                            }
                        }
                        
                    });
        }
    }
}
