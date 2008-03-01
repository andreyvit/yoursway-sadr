package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import java.util.Iterator;

import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.SimpleContinuation;

public class ConstructControlFlowTraverser {
    
    public void traverse(IConstruct construct, ContinuationRequestor requestor, ConstructVisitor visitor,
            SimpleContinuation continuation) {
        traverse(construct, requestor, visitor, true, continuation);
    }
    
    public void traverse(IConstruct construct, final ContinuationRequestor requestor,
            final ConstructVisitor visitor, final boolean callLeave, final SimpleContinuation afterTraverse) {
        final ConstructVisitor subvisitor = visitor.enter(construct);
        if (subvisitor == null)
            afterTraverse.run(requestor);
        else {
            construct.calculateEffectiveControlFlowGraph(requestor, new ControlFlowGraphRequestor() {
                
                public void process(ControlFlowGraph effectiveGraph, ContinuationRequestor requestor) {
                    boolean callLeaveOnChildren = (subvisitor != visitor);
                    Iterator<IConstruct> iterator = effectiveGraph.getNodes().iterator();
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
                        final ConstructVisitor visitor, final boolean callLeaveOnChildren,
                        final Iterator<IConstruct> iterator, final SimpleContinuation afterTraverseChildren) {
                    if (iterator.hasNext()) {
                        IConstruct subnode = iterator.next();
                        traverse(subnode, requestor, visitor, callLeaveOnChildren, new SimpleContinuation() {
                            
                            public void run(ContinuationRequestor requestor) {
                                traverseSubnodes(requestor, visitor, callLeaveOnChildren, iterator,
                                        afterTraverseChildren);
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
