package com.yoursway.sadr.python.analysis.lang.constructs;

import static com.google.common.collect.Lists.newArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.propagation.PropagationTracker;
import com.yoursway.sadr.core.propagation.PropagationTrackerImpl;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonScope;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.MethodDeclarationC;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.PythonFileC;

public abstract class PythonScopeImpl<N extends ASTNode> extends PythonConstructImpl<N> implements
        PythonStaticContext {
    
    private final Set<String> globalVariables = new HashSet<String>();
    
    private final Set<String> localVariables = new HashSet<String>();
    
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
    
    public void addGlobalVariable(String name) {
        if (name == null)
            throw new NullPointerException("name is null");
        globalVariables.add(name);
    }
    
    public boolean isGlobalVariable(String name) {
        if (name == null)
            throw new NullPointerException("name is null");
        return isGlobalScope() || globalVariables.contains(name);
    }
    
    public boolean isGlobalScope() {
        return false;
    }
    
    public void addLocalVariable(String name) {
        if (name == null)
            throw new NullPointerException("name is null");
        localVariables.add(name);
    }
    
    public boolean isLocalVariable(String name) {
        if (name == null)
            throw new NullPointerException("name is null");
        return localVariables.contains(name);
    }
    
    public PythonScope findDefiningScope(String name) {
        if (isGlobalVariable(name))
            return fileC();
        for (PythonScope scope = this; scope != null; scope = scope.parentScope())
            if (scope.isLocalVariable(name))
                return scope;
        return fileC();
    }
    
    public List<PythonScope> currentScopesIncludingSelf() {
        final List<PythonScope> scopes = newArrayList();
        for (PythonScope scope = this; scope != null; scope = scope.parentScope())
            scopes.add(scope);
        return scopes;
    }
    
}