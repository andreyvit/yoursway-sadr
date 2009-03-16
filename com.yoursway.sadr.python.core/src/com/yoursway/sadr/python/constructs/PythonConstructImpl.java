package com.yoursway.sadr.python.constructs;

import static com.yoursway.sadr.engine.util.Lists.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.google.common.base.Predicate;
import com.yoursway.sadr.core.constructs.ControlFlowGraph;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;

public abstract class PythonConstructImpl<N extends ASTNode> implements PythonConstruct {
    
    protected final N node;
    private final PythonStaticContext parentScope;
    private List<PythonConstruct> children = Collections.emptyList();
    private ControlFlowGraph<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> effectiveControlFlowGraph;
    
    public PythonConstructImpl(PythonStaticContext sc, N node, PythonConstructImpl<?> parent) {
        if (sc == null)
            sc = (PythonStaticContext) this;
        if (node == null)
            throw new NullPointerException("node is null");
        this.parentScope = sc;
        this.node = node;
        if (parent != null)
            parent.addChild(this);
    }
    
    void addChild(PythonConstruct child) {
        if (children.isEmpty())
            children = new ArrayList<PythonConstruct>();
        children.add(child);
    }
    
    public N node() {
        return node;
    }
    
    public PythonConstruct subconstructFor(ASTNode node) {
        PythonConstruct result = innerSubsonstructFor(node);
        if (result == null)
            throw new IllegalArgumentException("Subconstruct not found for node "
                    + node.getClass().getSimpleName() + " in " + this);
        return result;
    }
    
    private PythonConstruct innerSubsonstructFor(ASTNode node) {
        if (isNode(node))
            return this;
        for (PythonConstruct c : children) {
            PythonConstruct sc = ((PythonConstructImpl<?>) c).innerSubsonstructFor(node);
            if (sc != null)
                return sc;
        }
        return null;
    }
    
    protected boolean isNode(ASTNode node) {
        return node == this.node;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((node == null) ? 0 : node.hashCode());
        //FIXME: Add root element comparison, to check nodes belong to the same tree!
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PythonConstructImpl other = (PythonConstructImpl) obj;
        //FIXME: Add root element comparison, to check nodes belong to the same tree!
        return (node == other.node);
    }
    
    public final PythonStaticContext staticContext() {
        return parentScope;
    }
    
    @Override
    public String toString() {
        return node.toString();
    }
    
    public PythonFileC fileC() {
        return parentScope.fileC();
    }
    
    @pausable
    public final ControlFlowGraph<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> calculateEffectiveControlFlowGraph() {
        if (effectiveControlFlowGraph != null)
            return effectiveControlFlowGraph;
        else
            return doCalculateEffectiveControlFlowGraph();
    }
    
    @pausable
    protected ControlFlowGraph<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> doCalculateEffectiveControlFlowGraph() {
        List<PythonConstruct> constructs = filter(enclosedConstructs(), NOT_METHOD);
        effectiveControlFlowGraph = ControlFlowGraph.create(constructs);
        return effectiveControlFlowGraph;
    }
    
    protected static final Predicate<PythonConstruct> NOT_METHOD = new Predicate<PythonConstruct>() {
        
        public boolean apply(PythonConstruct t) {
            return !(t instanceof MethodDeclarationC);
        }
        
    };
    
    public List<PythonConstruct> enclosedConstructs() {
        return staticallyEnclosedConstructs();
    }
    
    protected final List<PythonConstruct> staticallyEnclosedConstructs() {
        return children;
    }
    
    protected PythonConstruct wrap(ASTNode node, PythonStaticContext staticContext) {
        return PythonConstructFactory.wrap(node, staticContext, this);
    }
    
    protected List<PythonConstruct> wrap(List<ASTNode> node, PythonStaticContext staticContext) {
        return PythonConstructFactory.wrap(node, staticContext, this);
    }
    
}
