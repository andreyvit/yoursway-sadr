package com.yoursway.sadr.python.constructs;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.propagation.PropagationTracker;
import com.yoursway.sadr.core.propagation.PropagationTrackerImpl;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;

public abstract class PythonScopeImpl<N extends ASTNode> extends PythonConstructImpl<N> implements
        PythonStaticContext {
    
    public PythonScopeImpl(PythonStaticContext sc, N node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    public abstract String name();
    
    @Override
    public abstract String toString();
    
    public PythonFileC getFileScope() {
        return parentScope().getFileScope();
    }
    
    public PythonStaticContext parentScope() {
        return staticContext();
    }
    
    public PropagationTracker<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> propagationTracker() {
        return new PropagationTrackerImpl<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode>();
    }
    
    public MethodDeclarationC getParentMethodDeclarationC() {
        return parentScope().getParentMethodDeclarationC();
    }
    
    public PythonStaticContext scopeContext() {
        return this;
    }
    
}
