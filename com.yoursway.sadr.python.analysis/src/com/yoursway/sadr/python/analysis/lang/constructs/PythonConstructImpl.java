package com.yoursway.sadr.python.analysis.lang.constructs;

import static com.yoursway.sadr.engine.util.Lists.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.google.common.base.Predicate;
import com.yoursway.sadr.core.constructs.ControlFlowGraph;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.context.lexical.scopes.PythonScope;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.MethodDeclarationC;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.PythonConstructFactory;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.PythonFileC;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public abstract class PythonConstructImpl<N extends ASTNode> implements PythonConstruct {
    
    protected final N node;
    private final PythonLexicalContext parentScope;
    private List<PythonConstruct> children = Collections.emptyList();
    private ControlFlowGraph<PythonConstruct, PythonLexicalContext, PythonDynamicContext, ASTNode> effectiveControlFlowGraph;
    private final PythonConstructImpl<?> parent;
    
    public PythonConstructImpl(PythonLexicalContext sc, N node, PythonConstructImpl<?> parent) {
        this.parent = parent;
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
    
    public PythonFileC fileC() {
        return parent.fileC();
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
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        PythonConstructImpl<?> that = (PythonConstructImpl<?>) obj;
        return this.node.equals(that.node);
    }
    
    public final PythonLexicalContext staticContext() {
        return parentScope;
    }
    
    @Override
    public String toString() {
        return node.toString();
    }
    
    @pausable
    public final ControlFlowGraph<PythonConstruct, PythonLexicalContext, PythonDynamicContext, ASTNode> calculateEffectiveControlFlowGraph() {
        if (effectiveControlFlowGraph != null)
            return effectiveControlFlowGraph;
        else
            return doCalculateEffectiveControlFlowGraph();
    }
    
    @pausable
    protected ControlFlowGraph<PythonConstruct, PythonLexicalContext, PythonDynamicContext, ASTNode> doCalculateEffectiveControlFlowGraph() {
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
    
    protected PythonConstruct wrap(ASTNode node, PythonLexicalContext staticContext) {
        return PythonConstructFactory.wrap(node, staticContext, this);
    }
    
    protected PythonConstruct wrapOrNull(ASTNode node, PythonLexicalContext staticContext) {
        if (node == null)
            return null;
        return PythonConstructFactory.wrap(node, staticContext, this);
    }
    
    protected List<PythonConstruct> wrap(List<ASTNode> node, PythonLexicalContext staticContext) {
        return PythonConstructFactory.wrap(node, staticContext, this);
    }
    
    public Unode toUnode() {
        return null;
    }
    
    public List<PythonScope> currentScopes() {
        return staticContext().getScope().currentScopesIncludingSelf();
    }
    
    public Bnode toBnode() {
        Unode unode = toUnode();
        if (unode == null)
            return null;
        return new Bnode(unode, staticContext());
    }
    
    @pausable
    public final PythonValueSet evaluateValue(PythonDynamicContext dc) {
        throw new UnsupportedOperationException();
    }
    
}
