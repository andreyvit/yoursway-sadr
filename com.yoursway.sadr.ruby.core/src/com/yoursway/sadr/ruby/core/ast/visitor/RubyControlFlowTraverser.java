package com.yoursway.sadr.ruby.core.ast.visitor;

import static com.yoursway.sadr.engine.util.Lists.combineLists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ruby.ast.RubyIfStatement;

import com.yoursway.sadr.engine.ContextSensitiveThing;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.ruby.core.typeinferencing.values.Value;
import com.yoursway.sadr.ruby.core.typeinferencing.values.ValueTraits;
import com.yoursway.sadr.ruby.core.typeinferencing.valuesets.ValueSet;

public class RubyControlFlowTraverser {
    
    static class ControlFlowGraph {
        
        private final List<ASTNode> nodes;
        
        public ControlFlowGraph(List<ASTNode> nodes) {
            this.nodes = nodes;
        }
        
        public List<ASTNode> getNodes() {
            return nodes;
        }
        
    }
    
    interface ControlFlowGraphRequestor {
        
        void process(ControlFlowGraph effectiveGraph, ContinuationRequestor requestor);
        
    }
    
    private final Scope scope;
    private final ContextSensitiveThing thing;
    
    public RubyControlFlowTraverser(ContextSensitiveThing thing, Scope scope) {
        this.thing = thing;
        this.scope = scope;
    }
    
    public void traverse(ASTNode node, ContinuationRequestor requestor, RubyAstVisitor<?> rootVisitor,
            SimpleContinuation afterTraverse) {
        traverse(node, requestor, rootVisitor, true, afterTraverse);
    }
    
    public void traverse(ASTNode node, final ContinuationRequestor requestor,
            final RubyAstVisitor<?> visitor, final boolean callLeave, final SimpleContinuation afterTraverse) {
        final RubyAstVisitor<?> subvisitor = visitor.switchEnter(node);
        if (subvisitor == null)
            afterTraverse.run(requestor);
        else {
            calculateEffectiveControlFlowGraph(node, requestor, new ControlFlowGraphRequestor() {
                
                public void process(ControlFlowGraph effectiveGraph, ContinuationRequestor requestor) {
                    boolean callLeaveOnChildren = (subvisitor != visitor);
                    Iterator<ASTNode> iterator = effectiveGraph.getNodes().iterator();
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
                        final RubyAstVisitor<?> subvisitor, final boolean callLeaveOnChildren,
                        final Iterator<ASTNode> iterator, final SimpleContinuation afterTraverseChildren) {
                    if (iterator.hasNext()) {
                        ASTNode subnode = iterator.next();
                        traverse(subnode, requestor, subvisitor, callLeaveOnChildren,
                                new SimpleContinuation() {
                                    
                                    public void run(ContinuationRequestor requestor) {
                                        traverseSubnodes(requestor, subvisitor, callLeaveOnChildren,
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
    
    private void calculateEffectiveControlFlowGraph(ASTNode node, ContinuationRequestor requestor,
            final ControlFlowGraphRequestor cfgRequestor) {
        final Collection<ModuleDeclaration> dynamicNodes = scope.nodeLookup().extentionsOf(node);
        if (node instanceof RubyIfStatement) {
            calculateEffectiveIfStatementFlow(requestor, cfgRequestor, dynamicNodes, (RubyIfStatement) node);
        } else {
            calculateStaticFlow(node, requestor, cfgRequestor, dynamicNodes);
        }
    }
    
    @SuppressWarnings("unchecked")
    private void calculateStaticFlow(ASTNode node, ContinuationRequestor requestor,
            final ControlFlowGraphRequestor cfgRequestor, final Collection<ModuleDeclaration> dynamicNodes) {
        List<ASTNode> staticNodes = node.getChilds();
        cfgRequestor.process(new ControlFlowGraph(combineLists(staticNodes, dynamicNodes)), requestor);
    }
    
    private void calculateEffectiveIfStatementFlow(ContinuationRequestor requestor,
            final ControlFlowGraphRequestor cfgRequestor, final Collection<ModuleDeclaration> dynamicNodes,
            final RubyIfStatement ifStat) {
        final ASTNode condition = ifStat.getCondition();
        final Scope subscope = RubyUtils.restoreSubscope(scope, condition);
        requestor.subgoal(new Continuation() {
            
            ValueInfoGoal conditionGoal = new ExpressionValueInfoGoal(subscope, condition, InfoKind.VALUE);
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                requestor.subgoal(conditionGoal);
            }
            
            public void done(ContinuationRequestor requestor) {
                ValueSet valueSet = conditionGoal.result(thing).getValueSet();
                boolean visitThen = false, visitElse = false;
                for (Value value : valueSet.containedValues()) {
                    ValueTraits tr = value.traits();
                    if (tr.cohersibleToBoolean())
                        if (tr.coherseToBoolean())
                            visitThen = true;
                        else
                            visitElse = true;
                }
                if (!visitThen && !visitElse)
                    visitThen = visitElse = true;
                List<ASTNode> staticNodes = new ArrayList<ASTNode>();
                if (visitThen)
                    staticNodes.add(ifStat.getThen());
                if (visitElse)
                    staticNodes.add(ifStat.getElse());
                cfgRequestor
                        .process(new ControlFlowGraph(combineLists(staticNodes, dynamicNodes)), requestor);
            }
            
        });
    }
    
}
